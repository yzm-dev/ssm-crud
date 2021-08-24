import com.alibaba.druid.pool.DruidDataSource;
import com.ssm.mapper.EmployeeMapper;
import com.ssm.pojo.Employee;
import com.ssm.pojo.EmployeeExample;
import com.ssm.utils.Msg;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class Test {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @org.junit.Test
    public void test1() {
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria employeeExampleCriteria = employeeExample.createCriteria();
        // 插入 1000 条员工数据
        for (int i = 0; i < 1000; i++) {
            String uuid = UUID.randomUUID().toString().substring(0,8);
            int d_id = new Random().nextInt(4) + 1;
            String gender = "M";
            if (d_id % 2 == 0) {
                gender = "F";
            }
            Employee employee = new Employee(null, uuid, gender, uuid + "@qq.com",d_id, null);
            employeeMapper.insertSelective(employee);
        }
    }

    @org.junit.Test
    public void test2() throws Exception {
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/SSM_CRUD/xxx"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println(contentAsString);
    }

}