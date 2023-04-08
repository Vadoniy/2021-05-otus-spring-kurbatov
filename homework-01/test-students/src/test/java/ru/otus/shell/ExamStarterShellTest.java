package ru.otus.shell;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import ru.otus.service.ExamService;
import ru.otus.service.FileNameProvider;
import ru.otus.service.LocaleProvider;
import ru.otus.service.LocalizationDisplayService;

import java.util.regex.Pattern;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExamStarterShellTest {

    @Autowired
    private Shell shell;

    @Autowired
    private LocalizationDisplayService localizationDisplayService;

    @MockBean
    private ExamService examService;

    @MockBean
    private LocaleProvider localeProvider;

    @MockBean
    private FileNameProvider fileNameProvider;

    @Test
    void testLocaleChange() {
        when(localeProvider.getLocale()).thenReturn("ru");
        Assertions.assertTrue(Pattern.matches(".*\\p{InCyrillic}.*",
                localizationDisplayService.getLocalizedMessage("info.greeting")));
        when(localeProvider.getLocale()).thenReturn("en");
        Assertions.assertFalse(Pattern.matches(".*\\p{InCyrillic}.*",
                localizationDisplayService.getLocalizedMessage("info.greeting")));
    }

    @Test
    void startExam() {
        shell.evaluate(() -> "start");
        verify(examService).startExam();
    }
}