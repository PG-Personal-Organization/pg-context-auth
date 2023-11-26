package pg.context.auth.spring.delivery.http.frontend;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pg.context.auth.domain.user.User;
import pg.context.auth.domain.user.UserService;

import java.util.List;

import static pg.context.auth.api.frontend.HttpServicesPaths.USER_PATH;

/**
 * The type Users http endpoint.
 */
@Log4j2
@RestController
@RequestMapping(USER_PATH)
@RequiredArgsConstructor
@Tag(name = "Users")
public class UsersHttpEndpoint {
    private final UserService userService;

    /**
     * Fetch all users list.
     *
     * @return the list
     */
    @GetMapping
    public List<User> fetchAllUsers() {
        return userService.findAllUsers();
    }
}