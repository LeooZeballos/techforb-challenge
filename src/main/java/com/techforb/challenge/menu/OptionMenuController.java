package com.techforb.challenge.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/option-menu")
@RequiredArgsConstructor
public class OptionMenuController {

    /**
     * The service of the option menu.
     */
    private final IOptionMenuService optionMenuService;

    /**
     * Gets a menu by its name.
     *
     * @param name The name of the menu.
     * @return The menu.
     */
    @GetMapping
    public ResponseEntity<OptionMenu> getOptionMenuByName(@RequestParam(defaultValue = "default", required = false) String name) {
        try {
            return ResponseEntity.ok(optionMenuService.getOptionMenuByName(name));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
