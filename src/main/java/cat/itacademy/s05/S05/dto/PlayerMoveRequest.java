package cat.itacademy.s05.S05.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class PlayerMoveRequest {
    @NotBlank(message = "Move cannot be empty.")
    private String move;
}
