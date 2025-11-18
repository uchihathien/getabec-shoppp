package vn.fs.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vn.fs.commom.CommomDataService;
import vn.fs.model.dto.ProfileDTO;
import vn.fs.model.entities.Order;
import vn.fs.model.entities.OrderDetail;
import vn.fs.model.entities.User;
import vn.fs.repository.OrderDetailRepository;
import vn.fs.repository.OrderRepository;
import vn.fs.repository.UserRepository;

@Controller
public class ProfileController extends CommomController {

	@Value("${upload.path}")
	private String pathUploadImage;

	@Autowired
	UserRepository userRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Autowired
	CommomDataService commomDataService;

	@GetMapping("/profile")
	public String profile(Model model, Principal principal, User user,
						  @RequestParam("page") Optional<Integer> page,
						  @RequestParam("size") Optional<Integer> size) {

		if (principal != null) {
			user = userRepository.findByEmail(principal.getName());
			model.addAttribute("user", user);
			model.addAttribute("profileRequest", user);
		}

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(6);

		Page<Order> orderPage = findPaginated(PageRequest.of(currentPage - 1, pageSize), user);
		int totalPages = orderPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		commomDataService.commonData(model, user);
		model.addAttribute("orderByUser", orderPage);

		return "web/profile";
	}

	@PostMapping("/editProfile/{id}")
	public String editProfile(@PathVariable("id") Long id,
							  @ModelAttribute("profileRequest") ProfileDTO profileRequest,
							  @RequestParam("file") MultipartFile file,
							  ModelMap model) {
		try {
			Optional<User> userOpt = userRepository.findById(id);
			if (userOpt.isEmpty()) {
				model.addAttribute("message", "Không tìm thấy người dùng");
				return "redirect:/profile";
			}

			User user = userOpt.get();

			// Xử lý upload ảnh
			String avatar = user.getAvatar();
			if (!file.isEmpty()) {
				File uploadDir = new File(pathUploadImage);
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}

				avatar = file.getOriginalFilename();
				File destination = new File(uploadDir, avatar);
				file.transferTo(destination);
			}

			// Cập nhật thông tin
			user.setName(profileRequest.getName());
			user.setAddress(profileRequest.getAddress());
			user.setAvatar(avatar);

			userRepository.save(user);
			model.addAttribute("message", "Cập nhật thông tin thành công!");
			model.addAttribute("user", user);

		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("message", "Lỗi khi upload ảnh: " + e.getMessage());
		}

		return "redirect:/profile";
	}

	public Page<Order> findPaginated(Pageable pageable, User user) {
		List<Order> orderPage = orderRepository.findOrderByUserId(user.getUserId());
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Order> list;

		if (orderPage.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, orderPage.size());
			list = orderPage.subList(startItem, toIndex);
		}

		return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), orderPage.size());
	}

	@GetMapping("/order/detail/{order_id}")
	public ModelAndView detail(Model model, Principal principal,
							   User user, @PathVariable("order_id") Long id) {

		if (principal != null) {
			user = userRepository.findByEmail(principal.getName());
			model.addAttribute("user", user);
		}

		List<OrderDetail> listO = orderDetailRepository.findByOrderId(id);
		model.addAttribute("orderDetail", listO);
		commomDataService.commonData(model, user);

		return new ModelAndView("web/historyOrderDetail");
	}

	@GetMapping("/order/cancel/{order_id}")
	public ModelAndView cancel(ModelMap model, @PathVariable("order_id") Long id) {
		Optional<Order> opt = orderRepository.findById(id);
		if (opt.isEmpty()) return new ModelAndView("redirect:/profile", model);

		Order order = opt.get();
		order.setStatus((short) 3);
		orderRepository.save(order);

		return new ModelAndView("redirect:/profile", model);
	}
}
