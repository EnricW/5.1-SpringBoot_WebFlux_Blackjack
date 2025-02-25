package cat.itacademy.s05.S05.dto;

import cat.itacademy.s05.S05.enums.PlayerMove;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerMoveRequest {
    @NotNull(message = "Move cannot be null.")
    private PlayerMove move;
}
