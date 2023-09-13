package com.mindhub.homebanking;

import com.mindhub.homebanking.dtos.TransactionDto;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@SpringBootApplication
public class HomebankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
        return (args) -> {

            Client client3 = new Client("pepe", "admin", "admin@gmail.com", passwordEncoder.encode("1234"));
            Client client2 = new Client("Carla", "Perez", "carlaperez@hotmail.com", passwordEncoder.encode("1234"));
            Client client1 = new Client("Melba", "Morel", "melmo@gmail.com", passwordEncoder.encode("1234"));
            client1.setRole(Role.ADMIN);


            clientRepository.save(client1);
            clientRepository.save(client2);
            clientRepository.save(client3);

            Account account1 = new Account("VIN001", LocalDate.now(), 5000);
            Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);

            Account account3 = new Account("VIN003", LocalDate.now(), 12000);
            Account account4 = new Account("VIN004", LocalDate.now(), 13500);
            client2.addAccount(account1);
            client3.addAccount(account2);


            client1.addAccount(account3);
            client1.addAccount(account4);


            Transaction transaction = new Transaction(TransactionType.DEBIT, -2100, "Pago expensas", LocalDateTime.now());
            Transaction transaction2 = new Transaction(TransactionType.DEBIT, -1500, "Pago EDEA", LocalDateTime.of(2023, 8, 5, 19, 25, 31));
            Transaction transaction3 = new Transaction(TransactionType.CREDIT, 5600, "Honorarios", LocalDateTime.now());


            accountRepository.save(account3);
            accountRepository.save(account4);
            accountRepository.save(account1);
            accountRepository.save(account2);
            account1.addTransaction(transaction);
            account2.addTransaction(transaction2);
            account3.addTransaction(transaction);
            account3.addTransaction(transaction3);
            account4.addTransaction(transaction2);


            accountRepository.save(account1);
            accountRepository.save(account2);
            accountRepository.save(account3);
            accountRepository.save(account4);

            transactionRepository.save(transaction);
            transactionRepository.save(transaction3);
            transactionRepository.save(transaction2);

            clientRepository.save(client2);
            clientRepository.save(client3);

            Loan hipotecario = new Loan("hipotecario", 500000, Set.of(12, 24, 36, 48, 60));
            Loan personal = new Loan("Personal", 100000, Set.of(6, 12, 24));
            Loan automotriz = new Loan("Automotriz", 300000, Set.of(6, 12, 24, 36));

            loanRepository.save(hipotecario);
            loanRepository.save(personal);
            loanRepository.save(automotriz);

            ClientLoan clientLoan = new ClientLoan(Set.of(60));
            ClientLoan clientLoan2 = new ClientLoan(Set.of(12));
            client1.addClientLoan(clientLoan);
            client1.addClientLoan(clientLoan2);
            personal.setClientLoans(clientLoan2);
            hipotecario.setClientLoans(clientLoan);
            clientLoan2.getLoan().setMaxAmount(50000);
            clientLoan.getLoan().setMaxAmount(400000);
            clientRepository.save(client1);
            loanRepository.save(hipotecario);
            clientLoanRepository.save(clientLoan2);
            clientLoanRepository.save(clientLoan);

            TransactionDto transactionDto = new TransactionDto(transaction);

            /*Card debitGold = new Card(CardType.DEBIT, CardColor.GOLD);
            client1.addCard(debitGold);


            Card creditCard = new Card(CardType.CREDIT, CardColor.TITANIUM);
            client1.addCard(creditCard);*/

            /*cardRepository.save(creditCard);
            cardRepository.save(debitGold);*/
            clientRepository.save(client1);


        };
    }


}
