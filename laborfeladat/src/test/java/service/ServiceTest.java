package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceTest {

    static Service service;

    @BeforeClass
    @BeforeAll
    public static void  init() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();
        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");
        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    public void testSaveStudent() {
        int save = service.saveStudent("1", "Kis Anna", 532);
        service.deleteStudent("1");
        Assert.assertEquals(1, save);
    }

    @ParameterizedTest
    @ValueSource(strings = {"4","6"})
    public void testDeleteStudents(String id) {
        Iterable<Student> students = service.findAllStudents();
        Student student = null;
        Boolean felt = false;
        for(Student item: students) {
            if(item.getID().equals(id)) {
                felt = true;
                Assert.assertEquals(1, service.deleteStudent(id));
            }
        }
        if(felt == false) {
            Assert.assertEquals(0, service.deleteStudent(id));
        }

    }

    @Test
    public void testSaveHomework() {
        Iterable<Homework> homeworkBefore = service.findAllHomework();
        int countBefore = 0;
        for(Homework item: homeworkBefore) {
            countBefore++;
        }
        service.saveHomework("5", "Ez egy hazi", 3, 1);
        Iterable<Homework> homeworkAfter = service.findAllHomework();
        int countAfter = 0;
        for(Homework item: homeworkAfter) {
            countAfter++;
        }
        service.deleteHomework("5");
        Assert.assertTrue("Nem sikerult beszurni a homeworkot!", countAfter - countBefore == 1);
    }

    @Test
    public void testUpdateStudent() {
        int update = service.updateStudent("2", "Nagy Hunor", 532);
        Assert.assertEquals(1, update);
    }

    @Test
    public void testDeleteHomework() {
        Iterable<Homework> homeworkBefore = service.findAllHomework();
        int countBefore = 0;
        Homework homework = null;
        for(Homework item: homeworkBefore) {
            countBefore++;
            if(item.getID().equals("2"))
                homework = new Homework(item.getID(), item.getDescription(), item.getDeadline(), item.getStartline());
        }
        service.deleteHomework("2");
        Iterable<Homework> homeworkAfter = service.findAllHomework();
        int countAfter = 0;
        for(Homework item: homeworkAfter) {
            countAfter++;
        }
        service.saveHomework(homework.getID(), homework.getDescription(), homework.getDeadline(), homework.getStartline());
        Assert.assertNotEquals(countBefore ,countAfter);
    }
}