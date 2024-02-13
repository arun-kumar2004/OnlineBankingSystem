import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String fromAccount;
    private String toAccount;
    private double amount;

    public Transaction(String fromAccount, String toAccount, double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private String accountId;
    private double balance;
    private List<Transaction> transactions;

    public Account(String accountId) {
        this.accountId = accountId;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", accountId, amount));
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction(accountId, "Withdrawal", amount));
        } else {
            System.out.println("Insufficient Balance, Please check your Balance.");
        }
    }

    public void transfer(Account toAccount, double amount) {
        if (balance >= amount) {
            balance -= amount;
            toAccount.deposit(amount);
            transactions.add(new Transaction(accountId, toAccount.getAccountId(), amount));
            toAccount.transactions.add(new Transaction(accountId, toAccount.getAccountId(), amount));
            System.out.println("Money Transfer successfully");
        } else {
            System.out.println("Insufficient Balance, Please check your Balance.");
        }  
    }
}

class BankOperations {
    private List<Account> accounts;

    public BankOperations() {
        this.accounts = new ArrayList<>();
    }

    public void createAccount(String accountId) {
        accounts.add(new Account(accountId));
        System.out.println("Your Account successfully created with ID: " + accountId);
    }

    public Account getAccount(String accountId) {
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                return account;
            }
        }
        return null;
    }

    public void checkBalance(String accountId) {
        Account account = getAccount(accountId);
        if (account != null) {
            System.out.println("Account ID: " + account.getAccountId());
            System.out.println("your Account Balance is: " + account.getBalance());
        } else {
            System.out.println("Account not found");
        }
    }

    public void moneyTransfer(String fromAccountId, String toAccountId, double amount) {
        Account fromAccount = getAccount(fromAccountId);
        Account toAccount = getAccount(toAccountId);
        if (fromAccount != null && toAccount != null) {
            fromAccount.transfer(toAccount, amount);
        } else {
            System.out.println("One or both accounts not found");
        }
    }

    public void transactionHistory(String accountId) {
        Account account = getAccount(accountId);
        if (account != null) {
            System.out.println("Transaction History of Account ID: " + account.getAccountId());
            List<Transaction> transactions = account.getTransactions();
            for (Transaction transaction : transactions) {
                System.out.println("From: " + transaction.getFromAccount() +
                        " To: " + transaction.getToAccount() +
                        " Amount: " + transaction.getAmount());
            }
        } else {
            System.out.println("Account not found");
        }
    }
}

public class OnlineBankingSystem{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankOperations bankOperations = new BankOperations();
        
        while (true) {
            System.out.println("***** Select an option: *****");
            System.out.println("1. Create Account");
            System.out.println("2. Check Balance");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Money Transfer");
            System.out.println("6. Display Transaction History");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter account ID: ");
                    String accountId = scanner.next();
                    bankOperations.createAccount(accountId);
                    break;
                case 2:
                    System.out.print("Enter account ID: ");
                    accountId = scanner.next();
                    bankOperations.checkBalance(accountId); 
                    break;
                case 3:
                    System.out.print("Enter account ID: ");
                    accountId = scanner.next();
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    Account account=bankOperations.getAccount(accountId);
                    if(account!=null)
                    bankOperations.getAccount(accountId).deposit(depositAmount);
                    else
                    System.out.println("Account ID not match");
                    break;
                case 4:
                    System.out.print("Enter account ID: ");
                    accountId = scanner.next();
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    account=bankOperations.getAccount(accountId);
                    if(account!=null)
                    bankOperations.getAccount(accountId).withdraw(withdrawAmount);
                    else
                    System.out.println("Account ID not match");
                    break;
                case 5:
                    System.out.print("Enter from account ID: ");
                    String fromAccountId = scanner.next();
                    System.out.print("Enter to account ID: ");
                    String toAccountId = scanner.next();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    bankOperations.moneyTransfer(fromAccountId, toAccountId, amount);
                    break;
                case 6:
                    System.out.print("Enter account ID: ");
                    accountId = scanner.next();
                    bankOperations.transactionHistory(accountId);
                    break;
                case 7:
                    System.out.println("Exiting program");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice,Please try again");
            }
        }
    }
}
