package ro.chris.schlechta.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.chris.schlechta.StockAlarmsApplication;
import ro.chris.schlechta.dto.StockAlarmDto;
import ro.chris.schlechta.model.StockAlarm;
import ro.chris.schlechta.model.User;
import ro.chris.schlechta.repository.StockAlarmRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StockAlarmsApplication.class)
public class StockAlarmServiceTest {

    @MockBean
    StockAlarmRepository repository;

    @MockBean
    UserService userService;

    @MockBean
    EmailService emailService;

    @Autowired
    StockAlarmService service;

    @Test
    public void test_saveOrUpdateAlarm_alreadyExistsActiveAlarm() {
        Mockito.when(userService.getAuthenticatedUser()).thenReturn(new User().setEmail("dummy@email.com").setPassword("pass"));
        Mockito.when(repository.findByStockSymbol(any())).thenReturn(Optional.of(new StockAlarm().setActive(true)));

        StockAlarm stockAlarm = service.saveOrUpdateAlarm(new StockAlarmDto().setStockSymbol("AMZN"));

        Assert.assertNull(stockAlarm);
    }

    @Test
    public void test_saveOrUpdateAlarm_alreadyExistsInactiveAlarm() {
        Mockito.when(userService.getAuthenticatedUser()).thenReturn(new User().setEmail("dummy@email.com").setPassword("pass"));
        Mockito.when(repository.findByStockSymbol(any())).thenReturn(Optional.of(new StockAlarm().setActive(false)));
        Mockito.when(repository.save(any())).thenReturn(null);
        ArgumentCaptor<StockAlarm> alarmCaptor = ArgumentCaptor.forClass(StockAlarm.class);

        service.saveOrUpdateAlarm(
                new StockAlarmDto()
                        .setStockSymbol("AMZN")
                        .setCurrentPrice(200d)
                        .setActive(true)
        );
        Mockito.verify(repository).save(alarmCaptor.capture());

        Assert.assertTrue(alarmCaptor.getValue().isActive());
        Assert.assertEquals(Double.valueOf(200d), alarmCaptor.getValue().getCurrentPrice());
    }

    @Test
    public void test_saveOrUpdateAlarm_newAlarm() {
        Mockito.when(userService.getAuthenticatedUser()).thenReturn(new User().setEmail("dummy@email.com").setPassword("pass"));
        Mockito.when(repository.findByStockSymbol(any())).thenReturn(Optional.empty());
        Mockito.when(repository.save(any())).thenReturn(null);
        ArgumentCaptor<StockAlarm> alarmCaptor = ArgumentCaptor.forClass(StockAlarm.class);

        service.saveOrUpdateAlarm(createStockAlarmDto());
        Mockito.verify(repository).save(alarmCaptor.capture());

        Assert.assertEquals("AMZN", alarmCaptor.getValue().getStockSymbol());
        Assert.assertEquals(Double.valueOf(100), alarmCaptor.getValue().getInitialPrice());
        Assert.assertEquals(Double.valueOf(100), alarmCaptor.getValue().getCurrentPrice());
        Assert.assertEquals(Double.valueOf(10), alarmCaptor.getValue().getPositiveVariance());
        Assert.assertEquals(Double.valueOf(10), alarmCaptor.getValue().getNegativeVariance());
    }

    private StockAlarmDto createStockAlarmDto() {
        return new StockAlarmDto()
                .setStockSymbol("AMZN")
                .setInitialPrice(100d)
                .setCurrentPrice(100d)
                .setPositiveVariance(10d)
                .setNegativeVariance(10d);
    }

}