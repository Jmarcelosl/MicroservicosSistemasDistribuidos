package com.senai.ecommerce_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.senai.ecommerce_api.dto.UserDTO;
import com.senai.ecommerce_api.model.User;
import com.senai.ecommerce_api.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldGetAndConvertUsers() {
        User user = user("Ana", "111");
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDTO> users = userService.getAll();

        assertEquals("Ana", users.get(0).getNome());
    }

    @Test
    void shouldFindUserByIdOrFail() {
        User user = user("Ana", "111");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertEquals("Ana", userService.findById(1L).getNome());
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.findById(2L));
    }

    @Test
    void shouldSaveAndSetRegistrationDate() {
        UserDTO input = dto("Ana", "111");
        User saved = user("Ana", "111");
        when(userRepository.save(any(User.class))).thenReturn(saved);

        UserDTO result = userService.save(input);

        assertNotNull(input.getDataCadastro());
        assertEquals("Ana", result.getNome());
    }

    @Test
    void shouldDeleteExistingUser() {
        User user = user("Ana", "111");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository).delete(user);
    }

    @Test
    void shouldFindByCpfAndReturnNullWhenMissing() {
        User user = user("Ana", "111");
        when(userRepository.findByCpf("111")).thenReturn(user);
        assertEquals("Ana", userService.findByCpf("111").getNome());
        when(userRepository.findByCpf("222")).thenReturn(null);
        assertNull(userService.findByCpf("222"));
    }

    @Test
    void shouldQueryByNameAndEditAllowedFields() {
        User user = user("Ana", "111");
        when(userRepository.queryByNomeLike("Ana")).thenReturn(List.of(user));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        assertEquals(1, userService.queryByName("Ana").size());
        UserDTO changes = dto(null, null);
        changes.setEmail("ana@example.com");
        changes.setTelefone("999");
        UserDTO result = userService.editUser(1L, changes);

        assertEquals("ana@example.com", result.getEmail());
        assertEquals("999", result.getTelefone());
        assertEquals("Ana", result.getNome());
    }

    @Test
    void shouldReturnPagedUsers() {
        User user = user("Ana", "111");
        when(userRepository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(user)));

        assertEquals(1, userService.getAllPage(PageRequest.of(0, 10)).getTotalElements());
    }

    private User user(String name, String cpf) {
        User user = new User();
        user.setNome(name);
        user.setCpf(cpf);
        user.setEndereco("Rua A");
        user.setEmail("ana@example.com");
        user.setTelefone("888");
        return user;
    }

    private UserDTO dto(String name, String cpf) {
        UserDTO dto = new UserDTO();
        dto.setNome(name);
        dto.setCpf(cpf);
        dto.setEndereco("Rua A");
        dto.setEmail("ana@example.com");
        dto.setTelefone("888");
        return dto;
    }
}