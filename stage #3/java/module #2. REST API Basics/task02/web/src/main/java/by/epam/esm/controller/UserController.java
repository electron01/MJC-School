package by.epam.esm.controller;

import by.epam.esm.constant.WebConstant;
import by.epam.esm.dto.entity.OrderDto;
import by.epam.esm.dto.entity.OrderInfoDto;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.UserDto;
import by.epam.esm.service.OrderService;
import by.epam.esm.service.UserService;
import by.epam.esm.util.LinkUtil;
import by.epam.esm.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/users")
public class UserController implements PaginationController {
    private UserService userService;
    private OrderService orderService;

    @Autowired
    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;

    }


    @Override
    @GetMapping
    public ResponseEntity<PagedModel<UserDto>> findAll(WebRequest webRequest,
                                                       @RequestParam(required = false, defaultValue = WebConstant.PAGE_DEFAULT_VALUE) Integer page,
                                                       @RequestParam(required = false, defaultValue = WebConstant.LIMIT_DEFAULT_VALUE) Integer limit) {
        PaginationDto paginationDto = PaginationUtil.getPaginationDto(page, limit);
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        List<UserDto> userDtoList = userService.findAll(parameterMap, paginationDto);
        LinkUtil.addUserLinks(userDtoList);
        return ResponseEntity.ok(getPagedModel(userDtoList, paginationDto, webRequest, page));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable Long userId) {
        UserDto userDto = userService.findById(userId);
        LinkUtil.addUserLinks(List.of(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> addNewUser(@RequestBody UserDto userDto) {
        UserDto newUser = userService.add(userDto);
        return ResponseEntity.ok(newUser);

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long userId) {
        boolean wasDeleted = userService.delete(userId);
        if (wasDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/{userId}/order")
    public ResponseEntity<List<OrderDto>> getOrders(@PathVariable Long userId){
        List<OrderDto> orders = orderService.findByUserId(userId);
        LinkUtil.addOrderLinks(orders);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/{userId}/order")
    public ResponseEntity<OrderDto> addOrder(@PathVariable Long userId, @RequestBody List<OrderInfoDto>  orderInfoList){
        OrderDto orderDto = orderService.add(orderInfoList, userId);
        return ResponseEntity.ok(orderDto);

    }

    private PagedModel<UserDto> getPagedModel(List<UserDto> userDtoList, PaginationDto paginationDto, WebRequest webRequest, int page) {
        Map<String, String[]> params = webRequest.getParameterMap();
        int countOfElements = userService.getCountCountOfElements(params);
        PagedModel.PageMetadata pageMetadata = PaginationUtil.getPageMetaData(paginationDto, countOfElements);
        PagedModel<UserDto> tagsPagedModel = PagedModel.of(userDtoList, pageMetadata);
        LinkUtil.addPageLinks(tagsPagedModel, UserController.class, webRequest, paginationDto, page);
        return tagsPagedModel;
    }
}
