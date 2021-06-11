#### 手写Spring学习记录

##### 1. IOC与DI实现

1. 由DispatcherServlet.init()方法作为入口，实例ApplicationContext对象

   ```java
   public void init(ServletConfig config) {
       // 实例ApplicationContext对象
       this.applicationContext = new ApplicationContext(config.getInitParameter("contextConfigLocation"));
       this.initStrategies();
       LOGGER.info("Spring framework is init");
   }
   ```

2. 在ApplicationContext的构造方法中完成IOC与DI

   ```java
   public ApplicationContext(String configLocation) {
       LOGGER.info("Instantiate ApplicationContext");
       // 实例BeanDefinitionReade对象
       this.beanDefinitionReader = new BeanDefinitionReader(configLocation);
       // 加载需要注入到IOC中的Bean对象的定义
       List<BeanDefinition> beanDefinitions = this.beanDefinitionReader.loadBeanDefinitions();
       try {
           // 注册已加载的Bean对象的定义
           this.registerBeanDefinition(beanDefinitions);
           // 实例Bean对象以及完成DI操作
           this.autowired();
       } catch (BeanCreationException e) {
           LOGGER.error(e);
       }
   }
   ```

3. BeanDefinitionReader类的作用是SpringBean对象加载和筛选

4. BeanWrapper类是SpringBean对象的包装类，包含Bean对象的实例和Class属性

5. 循环依赖核心代码（`请阅读代码`）

 
##### 2. SpringMVC实现

1. 由DispatcherServlet.initStrategies()方法作为入口，该方法在ApplicationContext实例完成后被调用

   ```java
   public void init(ServletConfig config) {
       this.applicationContext = new ApplicationContext(config.getInitParameter("contextConfigLocation"));
       // 实例SpringMVC的组件
       this.initStrategies();
       LOGGER.info("Spring framework is init");
   }
   
   // ......
   
   private void initStrategies() {
       // 实例URL映射关系组件
       this.initHandlerMappings();
       // 实例动态参数适配器组件
       this.initHandlerAdapters();
       // 实例视图转换器组件
       this.initViewResolvers();
   }
   ```

2. HandlerMapping类存放url(接口地址)、controller(控制器实例)、method(接口对应方法)

3. HandlerAdapter.handle()方法的作用是解析HandlerMapping中method的形参，并通过HttpServletRequest封装实参，然后通过反射调用method，拿到接口响应值

4. ViewResolver类核心作用是根据接口响应值创建相应视图转换器

   ```java
   public View resolveViewName(Object result) {
       if (result instanceof ModelAndView) {
           // 模板引擎视图转换器
           ModelAndView mv = (ModelAndView) result;
           String viewName = mv.getViewName();
           if (!viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX)) {
               viewName = viewName + DEFAULT_TEMPLATE_SUFFIX;
           }
           String pathname = (templateDir.getPath() + "/" + viewName).replaceAll("/+", "/");
           File viewFile = new File(pathname);
           return new TemplateView(viewFile);
       } else if (result instanceof String) {
           // 字符串视图转换器
           return new StringView();
       } else {
           // 实体类视图转换器
           return new ObjectView();
       }
   }
   ```

5. View接口的render方法的作用是处理接口响应值



##### 3. SpringAOP实现

1. 在实例Bean对象时同时实例AdviceSupport对象，通过AdviceSupport对象判断该Bean对象是否匹配切面，若匹配则实例JDK动态代理对象并拿到Bean实例的动态代理对象放入到Bean实例缓存容器中

   ```java
   private Object instantiateBean(BeanDefinition beanDefinition) {
       String beanClassName = beanDefinition.getBeanClassName();
       Object instance = null;
       try {
           Class<?> clazz = Class.forName(beanClassName);
           if (this.factoryBeanObjectCache.containsKey(clazz)) {
               return this.factoryBeanObjectCache.get(clazz);
           }
           instance = clazz.newInstance();
           // 实例AdviceSupport对象
           AdviceSupport adviceSupport = this.getAdviceSupport();
           // 注入Bean实例
           adviceSupport.setTarget(instance);
           // 注入Bean实例的Class
           adviceSupport.setTargetClass(clazz);
           // 判断Bean实例的Class是否匹配切面
           if (adviceSupport.pointcutMatch()) {
               // 实例JDK动态代理对象，获取动态代理对象
               instance = new JdkDynamicAopProxy(adviceSupport).getProxy();
           }
           LOGGER.info("Instantiate bean: {}", beanClassName);
           this.factoryBeanObjectCache.put(clazz, instance);
       } catch (Exception e) {
           LOGGER.error(e, "实例Bean对象异常");
       }
       return instance;
   }
   ```

2. 对Bean对象封装为包装类时，需注意拿到的实例可能是代理对象，若是代理对象需解析出它的被代理实例，再拿到被代理实例的Class进行包装

   ```java
   // Bean包装类构造方法
   public BeanWrapper(Object wrapperInstance) throws Exception {
       this.wrapperInstance = wrapperInstance;
       this.wrapperClass = this.getTargetObjectClass(wrapperInstance);
   }
   
   // ......
   
   private Class<?> getTargetObjectClass(Object instance) throws Exception {
       // 判断Bean实例是否为代理对象
       if (!(instance instanceof Proxy)) {
           return instance.getClass();
       }
       // 获取代理对象的AdviceSupport实例
       AdviceSupport adviceSupport = AopTargetUtil
           .getTargetFieldValue(instance, JdkDynamicAopProxy.class, "adviceSupport", AdviceSupport.class);
       // 拿到被代理对象的Class
       return adviceSupport.getTargetClass();
   }
   ```

3. 对Bean对象进行DI操作时，也需注意从Bean包装类中获取的实例可能是代理对象，若是代理对象需解析出它的被代理实例，再进行DI

   ```java
   private void populateBean(BeanWrapper beanWrapper) {
       Object instance = beanWrapper.getWrapperInstance();
       // 判断Bean实例是否为代理对象
       if (instance instanceof Proxy) {
           try {
               // 获取代理对象的AdviceSupport实例
               AdviceSupport adviceSupport = AopTargetUtil
                   .getTargetFieldValue(instance, JdkDynamicAopProxy.class, "adviceSupport", AdviceSupport.class);
               // 拿到被代理对象
               instance = adviceSupport.getTarget();
           } catch (Exception e) {
               LOGGER.error(e, "获取代理对象实际对象失败");
           }
       }
       Class<?> clazz = beanWrapper.getWrapperClass();
       if (!clazz.isAnnotationPresent(Controller.class) && !clazz.isAnnotationPresent(Service.class)) {
           return;
       }
       try {
           Field[] fields = clazz.getDeclaredFields();
           for (Field field : fields) {
               if (!field.isAnnotationPresent(Autowired.class)) {
                   continue;
               }
               String autowiredBeanName = field.getType().getName();
               field.setAccessible(true);
               field.set(instance, this.getBean(autowiredBeanName));
           }
       } catch (Exception e) {
           LOGGER.error(e, "自动依赖注入异常");
       }
   }
   ```

4. 在解析Bean类的方法是否匹配切面时，当匹配上某个方法时，需要将该方法的接口方法也放入缓存中

   ```java
   public void setTargetClass(Class<?> targetClass) {
       this.targetClass = targetClass;
       this.parseClass();
   }
   
   // ......
   
   private void parseClass() {
       String pointcut = this.config.getPointcut();
       String pointcutClass = pointcut.substring(0, pointcut.lastIndexOf("."));
       // 切点Class正则表达式
       this.pointcutClassPattern = Pattern.compile("class " + pointcutClass.replaceAll("\\.", "\\\\."));
   
       try {
           this.methodCache = new HashMap<>();
           Class<?> aspectClass = Class.forName(this.config.getAspectClass());
           Map<String, Method> aspectMethods = new HashMap<>();
           for (Method method : aspectClass.getMethods()) {
               aspectMethods.put(method.getName(), method);
           }
           Pattern pointcutMethodPattern = Pattern.compile(pointcut.replaceAll("\\.", "\\\\."));
           // 遍历Bean实例Class的方法
           for (Method method : this.targetClass.getMethods()) {
               String methodString = method.toString();
               Matcher matcher = pointcutMethodPattern.matcher(methodString);
               // 判断方法是否匹配切面
               if (matcher.find()) {
                   Map<String, AdviceAspect> advices = new HashMap<>();
                   Object aspectObject = aspectClass.newInstance();
                   if (StrUtil.isNotBlank(this.config.getAspectBefore())
                       && aspectMethods.containsKey(this.config.getAspectBefore())) {
                       advices.put("before",
                                   new AdviceAspect(aspectObject, aspectMethods.get(this.config.getAspectBefore())));
                   }
                   if (StrUtil.isNotBlank(this.config.getAspectAfter())
                       && aspectMethods.containsKey(this.config.getAspectAfter())) {
                       advices.put("after",
                                   new AdviceAspect(aspectObject, aspectMethods.get(this.config.getAspectAfter())));
                   }
                   this.methodCache.put(method, advices);
                   // 获取Bean实例实现的接口类
                   Class<?>[] interfaces = this.targetClass.getInterfaces();
                   for (Class<?> clazz : interfaces) {
                       try {
                           // 获取接口中的该方法
                           Method m = clazz.getMethod(method.getName(), method.getParameterTypes());
                           // 将该方法也放入的缓存中
                           this.methodCache.put(m, advices);
                       } catch (Exception ignore) {
                       }
                   }
               }
           }
       } catch (Exception e) {
           LOGGER.error(e);
       }
   }
   ```

   
