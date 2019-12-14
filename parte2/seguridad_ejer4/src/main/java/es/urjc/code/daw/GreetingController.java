package es.urjc.code.daw;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

	@GetMapping("/greeting")
	public String greeting(Model model, @RequestParam String name) {

		model.addAttribute("name", this.sanitizeHTML(name));

		return "greeting_template";
	}

	private String sanitizeHTML(String untrustedHTML) {
		PolicyFactory policy = new HtmlPolicyBuilder()
			.allowStandardUrlProtocols().toFactory();

		if (policy.sanitize(untrustedHTML).isEmpty()) {
			return "REFLECTED XSS";
		}
		return policy.sanitize(untrustedHTML);
	}

}
