import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

// Manages individual account details and operations like checking balance, depositing, withdrawing, changing PIN, and maintaining transaction history.
class Account{
    private  String accountNumber;
    private  int pin;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String accountNumber, int pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory=new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public double checkBalance(){
        return  balance;
    }
    public  void deposit(double amount){
        if (amount > 0) {
            balance+=amount;
            transactionHistory.add(new Transaction("Deposit",amount));
            System.out.println("Deposit Successful.");
        }else {
            System.out.println("Invalid Depost Amount.");
        }
    }
    public void withdraw(double amount){
        if(amount>0 && amount<=balance){
            balance-=amount;
            transactionHistory.add(new Transaction("Withdraw",amount));
            System.out.println("Withdraw Successful.");
        }else{
            System.out.println("Insufficient funds or invalid amount.");
        }
    }
    public void changePin(int newPin){
        this.pin=newPin;
        System.out.println("PIN changed Successfully");
    }
    public List<Transaction> getTransactionHistory(){
        return transactionHistory;
    }
    public boolean authenticate(int enteredPin){
        return this.pin==enteredPin;
    }
}
//Records details of each transaction, including the type, amount, and timestamp.
class Transaction{
private  String  type;
private  Double  amount;
private LocalDateTime timestamp;

    public Transaction(String type, Double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return type +": ₹"+ amount + "on" + timestamp;
    }
}
//Simulates an ATM machine, handling user interactions and account management.
class ATM{
private Map<String,Account> accounts;

    public ATM() {
        this.accounts =new HashMap<>();
    }
    public void addAccount(Account account){
        accounts.put(account.getAccountNumber(), account);
    }
    public  void start(){
        Scanner sc= new Scanner(System.in);
        System.out.print("Enter Your Account Number: ");
        String accountNumber=sc.nextLine();
        Account account =accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }
        System.out.print("Enter PIN: ");
        int enteredPin=sc.nextInt();
        if (!account.authenticate(enteredPin)) {
            System.out.println("Incorrect PIN. ");
            return;
        }
        boolean exit = false;
            while(!exit){
                System.out.println("\n1. Check Balance");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Change PIN");
                System.out.println("5. Transaction History");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");
                int ch= sc.nextInt();
                switch (ch){
                    case 1:
                        System.out.println("Current Balance: ₹"+account.checkBalance());
                        break;
                    case 2:
                        System.out.print("Enter Deposit Amount: ");
                        double depositAmount=sc.nextDouble();
                        account.deposit(depositAmount);
                        break;
                    case 3:
                        System.out.print("Enter Withdrew Amount: ");
                        double withdrawAmount= sc.nextDouble();
                        account.withdraw(withdrawAmount);
                        break;
                    case 4:
                        System.out.print("Enter New PIN");
                        int newPin= sc.nextInt();
                        account.changePin(newPin);
                        break;
                    case 5:
                        System.out.println("Transaction History: ");
                        for(Transaction transaction:account.getTransactionHistory()){
                            System.out.println(transaction);
                        }
                        break;
                    case 6:
                        exit=true;
                        break;
                    default:
                        System.out.println("Invalid Option. Try Again. ");
                }
            }
            sc.close();
    }
}
//Initializes the ATM and adds a test account for demonstration purposes.
public class  Main{
    public static void main(String[] args) {
             //creating a Account
          Account account=new Account("123456789",123,1000.00);
          ATM atm=new ATM();
          atm.addAccount(account);
          atm.start();
    }
}