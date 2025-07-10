//boa pratica pra não expor banco de dados pra api
//dto - objetos q podem ser usados somente pra essa transferencia

package com.onebit.barbearia_fernandes.controller;

public record CreateUserDto(String nomeUsuario, String email, String telefone, String senha, String perfil) {
}


