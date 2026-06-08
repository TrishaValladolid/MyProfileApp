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

@Controller
public class HomeController {

    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private EducationDAO educationDAO;
    @Autowired
    private ProjectDAO projectDAO;
    @Autowired
    private ExperienceDAO experienceDAO;
    @Autowired
    private SkillDAO skillDAO;

    @GetMapping("/")
    public String profile(Model model) {

        // retrieve person data from the DAO
        Person person = personDAO.getPerson();
        model.addAttribute("person", person);

        // retrieve education data from the DAO
        List<Education> educationList = educationDAO.getEducation();
        model.addAttribute("educationList", educationList);

        // retrieve education details from the DAO
        List<EducationDetail> educationDetailList = educationDAO.getEducationDetails();
        model.addAttribute("educationDetailList", educationDetailList);

        // retrieve project data from the DAO
        List<Project> projectList = projectDAO.getProjects();
        model.addAttribute("projectList", projectList);

        // retrieve project details from the DAO
        List<ProjectDetail> projectDetailList = projectDAO.getProjectDetails();
        model.addAttribute("projectDetailList", projectDetailList);

        // group projects into chunks of 3 for the carousel
        List<List<Project>> projectChunks = new ArrayList<>();
        int chunkSize = 3;
        for (int i = 0; i < projectList.size(); i += chunkSize) {
            projectChunks.add(projectList.subList(i, Math.min(i + chunkSize, projectList.size())));
        }
        model.addAttribute("projectChunks", projectChunks);

        // retrieve experience data from the DAO
        List<Experience> experienceList = experienceDAO.getExperiences();
        model.addAttribute("experienceList", experienceList);

        // retrieve experience details from the DAO
        List<ExperienceDetail> experienceDetailList = experienceDAO.getExperienceDetails();
        model.addAttribute("experienceDetailList", experienceDetailList);

        // retrieve skills from the DAO
        List<Skill> skillList = skillDAO.getSkills();
        model.addAttribute("skillList", skillList);

        return "profile";
    }

    // GET - shows the Add Education form page
    @GetMapping("/education/add")
    public String showAddEducationForm() {
        return "addEducation";
    }

    // POST - receives the form data and saves it to the DB
    @PostMapping("/education/save")
    public String saveEducation(@ModelAttribute Education education,
                                @RequestParam(value = "coursework", required = false) List<String> coursework,
                                @RequestParam(value = "certifications", required = false) List<String> certifications) {
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

    // GET - shows the Add Project form page
    @GetMapping("/project/add")
    public String showAddProjectForm() {
        return "addProject";
    }

    // POST - receives the form data, saves the project and its details to the DB
    @PostMapping("/project/save")
    public String saveProject(@ModelAttribute Project project,
                              @RequestParam(value = "descriptions", required = false) List<String> descriptions) {
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

    // GET - shows the Edit Project form page pre-filled with existing data
    @GetMapping("/project/edit/{id}")
    public String showEditProjectForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("project", projectDAO.getProjectById(id));
        model.addAttribute("projectDetails", projectDAO.getProjectDetailsByProjectId(id));
        return "editProject";
    }

    // POST - updates the project and replaces its details in the DB
    @PostMapping("/project/update")
    public String updateProject(@ModelAttribute Project project,
                                @RequestParam(value = "descriptions", required = false) List<String> descriptions) {
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

    // GET - shows the Add Experience form page
    @GetMapping("/experience/add")
    public String showAddExperienceForm() {
        return "addExperience";
    }

    // POST - receives the form data, saves the experience and its details to the DB
    @PostMapping("/experience/save")
    public String saveExperience(@ModelAttribute Experience experience,
                                 @RequestParam(value = "descriptions", required = false) List<String> descriptions) {
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

    // GET - shows the Edit Experience form page pre-filled with existing data
    @GetMapping("/experience/edit/{id}")
    public String showEditExperienceForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("experience", experienceDAO.getExperienceById(id));
        model.addAttribute("experienceDetails", experienceDAO.getExperienceDetailsByExperienceId(id));
        return "editExperience";
    }

    // POST - updates the experience and replaces its details in the DB
    @PostMapping("/experience/update")
    public String updateExperience(@ModelAttribute Experience experience,
                                   @RequestParam(value = "descriptions", required = false) List<String> descriptions) {
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
}