package service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ServiceMockTest {
    @Mock
    private Service service;

    @Mock
    private StudentXMLRepository studentXMLRepository;

    @Mock
    private HomeworkXMLRepository homeworkXMLRepository;

    @Mock
    private GradeXMLRepository gradeXMLRepository;

    @BeforeEach
    @Before
    public void config() {
        studentXMLRepository = mock(StudentXMLRepository.class);
        homeworkXMLRepository = mock(HomeworkXMLRepository.class);
        gradeXMLRepository = mock(GradeXMLRepository.class);
        service = new Service(studentXMLRepository, homeworkXMLRepository, gradeXMLRepository);
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testSaveStudent(){
        Mockito.when(service.saveStudent("5","Nagy Alpar", 532)).thenReturn(1);
        int save = service.saveStudent("5","Nagy Alpar", 532);
        Assert.assertEquals(1, save);
        Mockito.verify(service).saveStudent("5","Nagy Alpar", 532);
    }

    @ParameterizedTest
    @ValueSource(strings = {"4", "6"})
    public void testDeleteStudents(String id){
        Mockito.when(service.deleteStudent(anyString())).thenReturn(1);
        int save = service.deleteStudent(id);
        Assert.assertEquals(1, save);
        Mockito.verify(service).deleteStudent(anyString());
    }

    @Test
    public void testUpdateStudent() {
        Mockito.when(service.updateStudent(anyString(),anyString(), anyInt())).thenReturn(1);
        int update = service.updateStudent("2","Nagy Alpar", 533);
        Assert.assertEquals(1, update);
        Mockito.verify(service).updateStudent(anyString(),anyString(), anyInt());
    }
}