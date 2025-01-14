package com.chat.application.room.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RequestRoomCreate(
        @NotBlank(message = "방 이름은 필수입니다")
        @Size(min = 2, max = 50, message = "방 이름은 2-50자 사이여야 합니다")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s]*$", message = "방 이름은 한글, 영문, 숫자만 가능합니다")
        String name,
        @Size(min = 4, max = 20, message = "비밀번호는 4-20자 사이여야 합니다")
        String password
) {
}