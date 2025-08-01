/*
 * Copyright 2014-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.WebSession;

// tag::class[]
@Controller
public class SessionController {

	@PostMapping("/session")
	public String setAttribute(@ModelAttribute SessionAttributeForm sessionAttributeForm, WebSession session) {
		session.getAttributes().put(sessionAttributeForm.getAttributeName(), sessionAttributeForm.getAttributeValue());
		return "redirect:/";
	}

	@GetMapping("/")
	public String index(Model model, WebSession webSession) {
		model.addAttribute("webSession", webSession);
		return "home";
	}

	private static final long serialVersionUID = 2878267318695777395L;

}
// tag::end[]
