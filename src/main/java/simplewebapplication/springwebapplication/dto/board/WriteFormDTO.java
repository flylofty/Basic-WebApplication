package simplewebapplication.springwebapplication.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WriteFormDTO {

    private String title;
    private String content;

    public WriteFormDTO() {
    }

    public WriteFormDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
