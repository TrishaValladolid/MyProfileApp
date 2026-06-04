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
        // add the person data to the model
        model.addAttribute("person", person);

        // retrieve education data from the DAO
        List<Education> educationList = educationDAO.getEducation();
        // add the education data to the model
        model.addAttribute("educationList", educationList);

        // retrieve education details from the DAO
        List<EducationDetail> educationDetailList = educationDAO.getEducationDetails();
        // add the education details to the model
        model.addAttribute("educationDetailList", educationDetailList);

        // retrieve project data from the DAO
        List<Project> projectList = projectDAO.getProjects();
        // add the project data to the model
        model.addAttribute("projectList", projectList);

        // retrieve project details from the DAO
        List<ProjectDetail> projectDetailList = projectDAO.getProjectDetails();
        // add the project details to the model
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
        return "addEducation"; // loads addEducation.html
    }

    // POST - receives the form data and saves it to the DB
    @PostMapping("/education/save")
    public String saveEducation(@ModelAttribute Education education) {
        educationDAO.insertEducation(education);
        return "redirect:/"; // goes back to profile after saving
    }
}