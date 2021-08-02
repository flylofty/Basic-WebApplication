package simplewebapplication.springwebapplication.web.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WriteForm {

    private String title;
    private String content;

    public WriteForm() {
    }

    public WriteForm(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
