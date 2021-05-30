package by.epam.esm.controller;

import by.epam.esm.constant.WebConstant;
import by.epam.esm.dto.entity.OrderDto;
import by.epam.esm.dto.entity.OrderInfoDto;
import by.epam.esm.dto.entity.PaginationDto;
import by.epam.esm.dto.entity.UserDto;
import by.epam.esm.service.OrderService;
import by.epam.esm.service.UserService;
import by.epam.esm.util.PaginationUtil;
import by.epam.esm.util.link.OrderLinkUtil;
import by.epam.esm.util.link.UserLinkUtil;
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
    private UserLinkUtil userLinkUtil;
    private OrderLinkUtil orderLinkUtil;

    @Autowired
    public UserController(UserService userService, OrderService orderService,UserLinkUtil userLinkUtil,OrderLinkUtil orderLinkUtil) {
        this.userService = userService;
        this.orderService = orderService;
        this.userLinkUtil = userLinkUtil;
        this.orderLinkUtil = orderLinkUtil;
    }

    /**
     * findAll
     * method for find User list
     * get mapping
     * @param webRequest - web parameters
     * @param page - number of page
     * @param limit - limit
     * @return user list
     */
    @Override
    @GetMapping
    public ResponseEntity<PagedModel<UserDto>> findAll(WebRequest webRequest,
                                                       @RequestParam(required = false, defaultValue = WebConstant.PAGE_DEFAULT_VALUE) Integer page,
                                                       @RequestParam(required = false, defaultValue = WebConstant.LIMIT_DEFAULT_VALUE) Integer limit) {
        PaginationDto paginationDto = PaginationUtil.getPaginationDto(page, limit);
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        List<UserDto> userDtoList = userService.findAll(parameterMap, paginationDto);
        userLinkUtil.addLinks(userDtoList);
        return ResponseEntity.ok(getPagedModel(userDtoList, paginationDto, webRequest, page));
    }

    /**
     * findById
     * method find user by id
     * @param userId - id for find
     * @return UserDto
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable Long userId) {
        UserDto userDto = userService.findById(userId);
        userLinkUtil.addLinks(List.of(userDto));
        return ResponseEntity.ok(userDto);
    }

    /**
     * addNewUser
     * method for create new user
     * PostMapping
     * @param userDto - UserDto for request
     * @return new User
     */
    @PostMapping
    public ResponseEntity<UserDto> addNewUser(@RequestBody UserDto userDto) {
        UserDto newUser = userService.add(userDto);
        return ResponseEntity.ok(newUser);
    }

    /**
     * deleteById
     * method for delete user by id
     * DeleteMapping
     * @param userId - id for delete
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long userId) {
        boolean wasDeleted = userService.delete(userId);
        if (wasDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    /**
     *
     * GetMapping
     * @param userId - find orders user
     * @return order list
     */
    @GetMapping("/{userId}/orders")
    public ResponseEntity<PagedModel<OrderDto>> getOrders(@PathVariable Long userId,
                                                    @RequestParam(required = false, defaultValue = WebConstant.PAGE_DEFAULT_VALUE) Integer page,
                                                    @RequestParam(required = false, defaultValue = WebConstant.LIMIT_DEFAULT_VALUE) Integer limit){
        PaginationDto paginationDto = PaginationUtil.getPaginationDto(page, limit);
        List<OrderDto> orders = orderService.findByUserId(userId,paginationDto);
        orderLinkUtil.addLinks(orders);
        return ResponseEntity.ok(getOrderPagedModel(orders,paginationDto,page,userId));
    }

    /**
     * addOrder
     * PostMapping
     * method for create new order
     * @param userId - user id
     * @param orderInfoList - interaction information
     * @return new Order
     */
    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderDto> addOrder(@PathVariable Long userId, @RequestBody List<OrderInfoDto>  orderInfoList){
        OrderDto orderDto = orderService.add(orderInfoList, userId);
        return ResponseEntity.ok(orderDto);
    }

    /**
     * method getPagedModel
     * @param page number of page
     * @param paginationDto - pagination dto
     * @param userDtoList - list for pagination
     * @param webRequest - web parameters
     * method creates pagination
     * @see by.epam.esm.dto.entity.PaginationDto
     * @return PageModel<UserDto>
     */
    private PagedModel<UserDto> getPagedModel(List<UserDto> userDtoList, PaginationDto paginationDto, WebRequest webRequest, int page) {
        Map<String, String[]> params = webRequest.getParameterMap();
        int countOfElements = userService.getCountCountOfElements(params);
        PagedModel.PageMetadata pageMetadata = PaginationUtil.getPageMetaData(paginationDto, countOfElements);
        PagedModel<UserDto> tagsPagedModel = PagedModel.of(userDtoList, pageMetadata);
        userLinkUtil.addPageLinks(tagsPagedModel, UserController.class, webRequest, paginationDto, page);
        return tagsPagedModel;
    }

    /**
     * method getOrderPagedModel
     * @param page number of page
     * @param paginationDto - pagination dto
     * @param orderList - list for pagination
     * @param userId - web parameter
     * method creates pagination
     * @see by.epam.esm.dto.entity.PaginationDto
     * @return PageModel<OrderDto>
     */
    private PagedModel<OrderDto> getOrderPagedModel(List<OrderDto> orderList, PaginationDto paginationDto, int page,Long userId) {
        int countOfElements = orderService.getCountCountOrderByUserId(userId);
        PagedModel.PageMetadata pageMetadata = PaginationUtil.getPageMetaData(paginationDto, countOfElements);
        PagedModel<OrderDto> tagsPagedModel = PagedModel.of(orderList, pageMetadata);
        userLinkUtil.addUserOrdersPagination(tagsPagedModel, UserController.class, paginationDto, page,userId);
        return tagsPagedModel;
    }
}
