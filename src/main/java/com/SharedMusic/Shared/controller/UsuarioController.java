package com.SharedMusic.Shared.controller;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SharedMusic.Shared.entity.Rol;
import com.SharedMusic.Shared.entity.Usuario;
import com.SharedMusic.Shared.enums.RolNombre;
import com.SharedMusic.Shared.service.RolService;
import com.SharedMusic.Shared.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	RolService rolService;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * Vista reqistro
	 */
	@GetMapping("/mantenimiento")
	public String registro(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "/usuario/mantenimiento";
	}

	/**
	 * 
	 * @param usuarioDto
	 * @param model
	 * @return
	 */
	@PostMapping("/actualizar")
	public ModelAndView actualizar(Usuario usuarioDto, Model model) {
		System.out.println("Si entro al registro");
		DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy/MMMM/dd HH:mm:ss");
		System.out.println("yyyy/MMMM/dd HH:mm:ss-> " + dtf3.format(LocalDateTime.now()));

		ModelAndView mv = new ModelAndView();

		String mensajeError = "";

		if (StringUtils.isBlank(usuarioDto.getNombreUsuario())) {
			mensajeError += "el usuario no puede estar vacío";
		}
		if (StringUtils.isBlank(usuarioDto.getPassword())) {
			mensajeError += "la contraseña no puede estar vacia";
		}
		if (StringUtils.isBlank(usuarioDto.getCorreo())) {
			mensajeError += "el correo no puede estar vacío";
		}

		if (StringUtils.isBlank(usuarioDto.getNombreCompleto())) {
			mensajeError += "el Nombre completo no puede estar vacío";
		}

		if (StringUtils.isBlank(usuarioDto.getCedula())) {
			mensajeError += "la cedula no puede estar vacia";
		}

		if (!StringUtils.isBlank(mensajeError)) {
			mv.setViewName("/usuario/mantenimiento");
			mv.addObject("error", mensajeError);
			return mv;
		}

		usuarioDto.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
		Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).get();
		Set<Rol> roles = new HashSet<>();
		roles.add(rolUser);
		usuarioDto.setRoles(roles);
		usuarioService.save(usuarioDto);
		return new ModelAndView("redirect:/usuario/lista");

	}

	@GetMapping("lista")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/usuario/lista");
		List<Usuario> usuarios = usuarioService.list();
		mv.addObject("usuarios", usuarios);
		return mv;
	}

	@GetMapping("/detalle/{id}")
	public ModelAndView detalle(@PathVariable("id") int id) {
		if (!usuarioService.existsById(id))
			return new ModelAndView("redirect:/usuario/lista");
		Usuario usuario = usuarioService.getById(id).get();
		ModelAndView mv = new ModelAndView("/usuario/detalle");
		mv.addObject("usuario", usuario);
		return mv;
	}

	@PostMapping(value = "/buscar")
	public ModelAndView buscar(@RequestParam String busquedaId, HttpServletRequest request,
			RedirectAttributes redirectAttrs) {
		int busId = 0;
		try {
			busId = Integer.parseInt(busquedaId);
		} catch (Exception e) {
		}
		if (!usuarioService.existsById(busId)) {
			redirectAttrs.addFlashAttribute("error", "Ese usuario no existe");
			return new ModelAndView("redirect:/usuario/lista");
		}
		Usuario usuario = usuarioService.getById(busId).get();
		ModelAndView mv = new ModelAndView("/usuario/detalle");
		mv.addObject("usuario", usuario);
		return mv;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") int id) {
		if (!usuarioService.existsById(id))
			return new ModelAndView("redirect:/usuario/lista");
		Usuario usuario = usuarioService.getById(id).get();
		ModelAndView mv = new ModelAndView("/usuario/mantenimiento");
		mv.addObject("usuario", usuario);
		return mv;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/borrar/{id}")
	public ModelAndView borrar(@PathVariable("id") int id) {
		try {
			if (usuarioService.existsById(id)) {
				usuarioService.delete(id);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("redirect:/usuario/lista");
	}
}
