package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.model.Education;
import com.example.demo.model.EducationDetail;
import com.example.demo.model.Project;
import com.example.demo.model.ProjectDetail;
import com.example.demo.model.Experience;
import com.example.demo.model.ExperienceDetail;
import com.example.demo.model.Skill;
import com.example.demo.dao.PersonDAO;
import com.example.demo.dao.EducationDAO;
import com.example.demo.dao.ProjectDAO;
import com.example.demo.dao.ExperienceDAO;
import com.example.demo.dao.SkillDAO;
import com.example.demo.dao.AdminDAO;
import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired private PersonDAO personDAO;
    @Autowired private EducationDAO educationDAO;
    @Autowired private ProjectDAO projectDAO;
    @Autowired private ExperienceDAO experienceDAO;
    @Autowired private SkillDAO skillDAO;
    @Autowired private AdminDAO adminDAO;

    private boolean isAdmin(HttpSession session) {
        return "admin".equals(session.getAttribute("role"));
    }

    @GetMapping("/")
    public String profile(HttpSession session, Model model) {
        if (session.getAttribute("role") == null) return "redirect:/landing";

        Person person = personDAO.getPerson();
        model.addAttribute("person", person);

        List<Education> educationList = educationDAO.getEducation();
        model.addAttribute("educationList", educationList);

        List<EducationDetail> educationDetailList = educationDAO.getEducationDetails();
        model.addAttribute("educationDetailList", educationDetailList);

        List<Project> projectList = projectDAO.getProjects();
        model.addAttribute("projectList", projectList);

        List<ProjectDetail> projectDetailList = projectDAO.getProjectDetails();
        model.addAttribute("projectDetailList", projectDetailList);

        List<List<Project>> projectChunks = new ArrayList<>();
        int chunkSize = 3;
        for (int i = 0; i < projectList.size(); i += chunkSize) {
            projectChunks.add(projectList.subList(i, Math.min(i + chunkSize, projectList.size())));
        }
        model.addAttribute("projectChunks", projectChunks);

        List<Experience> experienceList = experienceDAO.getExperiences();
        model.addAttribute("experienceList", experienceList);

        List<ExperienceDetail> experienceDetailList = experienceDAO.getExperienceDetails();
        model.addAttribute("experienceDetailList", experienceDetailList);

        List<Skill> skillList = skillDAO.getSkills();
        model.addAttribute("skillList", skillList);

        model.addAttribute("isAdmin", isAdmin(session));

        return "profile";
    }

    @GetMapping("/landing")
    public String landing() {
        return "landing";
    }

    @GetMapping("/guest")
    public String loginAsGuest(HttpSession session) {
        session.setAttribute("role", "guest");
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        if (adminDAO.validateAdmin(username, password)) {
            session.setAttribute("role", "admin");
            return "redirect:/";
        }
        model.addAttribute("error", "Invalid username or password.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/landing";
    }

    @GetMapping("/education/add")
    public String showAddEducationForm(HttpSession session) {
        if (!isAdmin(session)) return "redirect:/landing";
        return "addEducation";
    }

    @PostMapping("/education/save")
    public String saveEducation(@ModelAttribute Education education,
                                @RequestParam(value = "coursework", required = false) List<String> coursework,
                                @RequestParam(value = "certifications", required = false) List<String> certifications,
                                HttpSession session) {
        if (!isAdmin(session)) return "redirect:/landing";

        educationDAO.insertEducation(education);
        int newId = educationDAO.getLastInsertedEducationId();

        if (coursework != null) {
            for (String c : coursework) {
                if (c != null && !c.trim().isEmpty()) {
                    educationDAO.insertEducationDetail(newId, "Coursework", c.trim());
                }
            }
        }

        if (certifications != null) {
            for (String c : certifications) {
                if (c != null && !c.trim().isEmpty()) {
                    educationDAO.insertEducationDetail(newId, "Certification", c.trim());
                }
            }
        }

        return "redirect:/";
    }

    @GetMapping("/project/add")
    public String showAddProjectForm(HttpSession session) {
        if (!isAdmin(session)) return "redirect:/landing";
        return "addProject";
    }

    @PostMapping("/project/save")
    public String saveProject(@ModelAttribute Project project,
                              @RequestParam(value = "descriptions", required = false) List<String> descriptions,
                              HttpSession session) {
        if (!isAdmin(session)) return "redirect:/landing";

        projectDAO.insertProject(project);
        if (descriptions != null) {
            int newId = projectDAO.getLastInsertedProjectId();
            for (String desc : descriptions) {
                if (desc != null && !desc.trim().isEmpty()) {
                    projectDAO.insertProjectDetail(newId, desc.trim());
                }
            }
        }
        return "redirect:/";
    }

    @GetMapping("/project/edit/{id}")
    public String showEditProjectForm(@PathVariable("id") int id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/landing";
        model.addAttribute("project", projectDAO.getProjectById(id));
        model.addAttribute("projectDetails", projectDAO.getProjectDetailsByProjectId(id));
        return "editProject";
    }

    @PostMapping("/project/update")
    public String updateProject(@ModelAttribute Project project,
                                @RequestParam(value = "descriptions", required = false) List<String> descriptions,
                                HttpSession session) {
        if (!isAdmin(session)) return "redirect:/landing";

        projectDAO.updateProject(project);
        projectDAO.deleteProjectDetailsByProjectId(project.getProjectId());
        if (descriptions != null) {
            for (String desc : descriptions) {
                if (desc != null && !desc.trim().isEmpty()) {
                    projectDAO.insertProjectDetail(project.getProjectId(), desc.trim());
                }
            }
        }
        return "redirect:/";
    }

    @GetMapping("/experience/add")
    public String showAddExperienceForm(HttpSession session) {
        if (!isAdmin(session)) return "redirect:/landing";
        return "addExperience";
    }

    @PostMapping("/experience/save")
    public String saveExperience(@ModelAttribute Experience experience,
                                 @RequestParam(value = "descriptions", required = false) List<String> descriptions,
                                 HttpSession session) {
        if (!isAdmin(session)) return "redirect:/landing";

        experienceDAO.insertExperience(experience);
        if (descriptions != null) {
            int newId = experienceDAO.getLastInsertedExperienceId();
            for (String desc : descriptions) {
                if (desc != null && !desc.trim().isEmpty()) {
                    experienceDAO.insertExperienceDetail(newId, desc.trim());
                }
            }
        }
        return "redirect:/";
    }

    @GetMapping("/experience/edit/{id}")
    public String showEditExperienceForm(@PathVariable("id") int id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/landing";
        model.addAttribute("experience", experienceDAO.getExperienceById(id));
        model.addAttribute("experienceDetails", experienceDAO.getExperienceDetailsByExperienceId(id));
        return "editExperience";
    }

    @PostMapping("/experience/update")
    public String updateExperience(@ModelAttribute Experience experience,
                                   @RequestParam(value = "descriptions", required = false) List<String> descriptions,
                                   HttpSession session) {
        if (!isAdmin(session)) return "redirect:/landing";

        experienceDAO.updateExperience(experience);
        experienceDAO.deleteExperienceDetailsByExperienceId(experience.getExperienceId());
        if (descriptions != null) {
            for (String desc : descriptions) {
                if (desc != null && !desc.trim().isEmpty()) {
                    experienceDAO.insertExperienceDetail(experience.getExperienceId(), desc.trim());
                }
            }
        }
        return "redirect:/";
    }

    @GetMapping("/skill/add")
    public String showAddSkillForm(HttpSession session) {
        if (!isAdmin(session)) return "redirect:/landing";
        return "addSkill";
    }

    @PostMapping("/skill/save")
    public String saveSkill(@ModelAttribute Skill skill, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/landing";
        skillDAO.insertSkill(skill);
        return "redirect:/";
    }

    @GetMapping("/skill/edit/{id}")
    public String showEditSkillForm(@PathVariable("id") int id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/landing";
        model.addAttribute("skill", skillDAO.getSkillById(id));
        return "editSkill";
    }

    @PostMapping("/skill/update")
    public String updateSkill(@ModelAttribute Skill skill, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/landing";
        skillDAO.updateSkill(skill);
        return "redirect:/";
    }
}