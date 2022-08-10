package ro.itschool.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.itschool.service.AccountService;

@Component
public class BillPayScheduler {

    @Autowired
    private AccountService accountService;

    //<second> <minute> <hour> <day-of-month> <month> <day-of-week>
    //0/x = every x seconds
    // – (range) => 0-5 seconds 0 to 5
    // https://www.baeldung.com/cron-expressions
    @Scheduled(cron = "0/10 * * * * *")
    public void payMyInterest() {
        accountService.getAllAccounts().stream()
                .peek(System.out::println)
                .filter(acc -> acc.getAmount() >= 10)
                .forEach(acc -> {
                    acc.setAmount(acc.getAmount() - 10D);
                    accountService.save(acc);
                });
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void notifyNotEnoughMoneyInAccount() {
        accountService.getAllAccounts().stream()
                .filter(acc -> acc.getAmount() < 10)
                .forEach(acc -> System.out.println("Sendig mail to iban: " + acc.getIban()));
    }
}
