package cat.itacademy.s05.S05.dto;

import cat.itacademy.s05.S05.enums.PlayerMove;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class PlayerMoveRequest {
    @NotBlank(message = "Move cannot be empty.")
    private String move;
}
