package com.onebit.barbearia_fernandes.dto.user;

import lombok.Builder;

@Builder
public record UserResumeResponseDto(
        Long userId,
        String name
) {
}
