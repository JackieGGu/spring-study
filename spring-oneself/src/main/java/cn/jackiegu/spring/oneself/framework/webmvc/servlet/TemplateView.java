package cn.jackiegu.spring.oneself.framework.webmvc.servlet;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模板视图
 *
 * @author JackieGu
 * @date 2021/3/8
 */
public class TemplateView implements View<ModelAndView> {

    private final File viewFile;

    public TemplateView(File viewFile) {
        this.viewFile = viewFile;
    }

    @Override
    public void render(ModelAndView result, HttpServletResponse response) throws Exception {
        Map<String, ?> model = result.getModel();
        StringBuilder sb = new StringBuilder();
        try (RandomAccessFile ra = new RandomAccessFile(this.viewFile, "r")) {
            // 解析模板并填充
            String line;
            Pattern pattern = Pattern.compile("\\$\\{[^}]+}");
            while ((line = ra.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    // group(): 得到匹配得子字符串
                    String key = matcher.group().replaceAll("\\$\\{|}", "");
                    if (model == null || !model.containsKey(key)) {
                        continue;
                    }
                    line = line.replaceAll("\\$\\{" + key + "}", String.valueOf(model.get(key)));
                    matcher = pattern.matcher(line);
                }
                sb.append(line);
            }
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().write(sb.toString());
        }
    }
}
