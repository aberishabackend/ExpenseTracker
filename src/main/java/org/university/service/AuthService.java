package org.university.service;

import org.university.dto.AppUserDTO;
import org.university.dto.AuthDTO;
import org.university.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO registerUser(AppUserDTO appUserDTO);
    AuthResponseDTO loginUser(AuthDTO authDTO);
}
