package com.uki.uber.user.service;


import com.uki.uber.user.UserModel;
import com.uki.uber.user.dao.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;




import java.io.File;
import java.io.IOException;



@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private  UserRepository userRepository;

    @Captor
    private ArgumentCaptor<UserModel> userModelArgumentCaptor;

    @InjectMocks
    private UserServiceImpl userService;

    private static UserModel user;
    private static File testImage;



    @BeforeEach
    public void init(){
         user = new UserModel(1L,"name","surname","123",
                null,"email@email.com","123456","address", null,null,null);

         testImage = new File("src/test/java/com/uki/uber/user/service/test-image.jpg");



    }

    @Test
    @DisplayName("Should create  new user without Multipart file image")
    public void shouldCreateNewUser(){
        userService.createNewUser(user, null);
        Mockito.verify(userRepository, Mockito.atMostOnce()).saveUser(userModelArgumentCaptor.capture());
        Assertions.assertThat(userModelArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(user);
    }


    @Test
    public void shouldCreateNewUserWithImageUpload() throws IOException {
        MockMultipartFile image = new MockMultipartFile("image","", MediaType.IMAGE_JPEG_VALUE, testImage.toURI().toURL().openStream());
        userService.createNewUser(user,image);
        Mockito.verify(userRepository, Mockito.atMostOnce()).saveUser(userModelArgumentCaptor.capture());
        Assertions.assertThat(userModelArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(user);
    }

    @AfterAll
    public static void tearDown() {
        File junkImage = new File(UserServiceImpl.UPLOAD_DIR_PATH + user.getFirstName() +  ".png");
        System.out.println(junkImage.delete());
    }


}