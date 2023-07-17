package com.techforb.challenge.menu;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OptionMenuServiceImplTest {
    private OptionMenuServiceImpl optionMenuService;

    @Mock
    private OptionMenuRepository optionMenuRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        optionMenuService = new OptionMenuServiceImpl(optionMenuRepository);
    }

    @Test
    void testGetOptionMenuByName_Exists() {
        String menuName = "main";
        OptionMenu optionMenu = new OptionMenu();
        optionMenu.setName(menuName);

        when(optionMenuRepository.findByName(menuName)).thenReturn(Optional.of(optionMenu));

        OptionMenu result = optionMenuService.getOptionMenuByName(menuName);

        assertEquals(optionMenu, result);

        verify(optionMenuRepository, times(1)).findByName(menuName);
    }

    @Test
    void testGetOptionMenuByName_NotExists() {
        String menuName = "main";

        when(optionMenuRepository.findByName(menuName)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> optionMenuService.getOptionMenuByName(menuName));

        verify(optionMenuRepository, times(1)).findByName(menuName);
    }

}
