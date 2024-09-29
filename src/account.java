import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class account
{
    static ArrayList<createAccount> accounts=new ArrayList<>();
    public static void main(String[] args) {

      Scanner s=new Scanner(System.in);
        System.out.print("Enter name: ");
        String name=s.nextLine();
        System.out.print("enter date of  birth: ");
        String dob=s.nextLine();
        System.out.print("enter phone number: ");
        String  phnnum=s.nextLine();
        System.out.print("Enter password: ");
        String password=s.nextLine();
        //creating new account object
        createAccount acc= new createAccount(name, dob, phnnum, password);
        accounts.add(acc);
        System.out.println("Your account Id is: "+acc.getAccountId());


        // taking input to verify details
        System.out.print("Enter Id for login: ");
        String Id=s.nextLine();
        System.out.print("Enter your password: ");
        String strongpass=s.nextLine();

        //objects of class useAccount and bankAccount
        useAccount user=new useAccount();
        bankAccount bank=new bankAccount(acc);

        if(user.verify(acc,Id, strongpass))
        {
            System.out.println("Login Successfully");
            boolean exit=false;
            while(!exit) {
                System.out.println("What you want to do? Choose any of these action: ");
                System.out.println("1. Transfer money");
                System.out.println("2. Deposit amount");
                System.out.println("3. Withdraw");
                System.out.println("4. Show current balance");
                System.out.println("5. View details of account");
                System.out.println("6. Exit");

                int choice = s.nextInt();
                s.nextLine();
                switch (choice) {
                    case 1:
                        //transfer money
                        System.out.print("Enter amount to be transfer: ");
                        double transferMoney=s.nextDouble();
                        s.nextLine();
                        System.out.print("Enter account Id in which money is to be transfer: ");
                        String id=s.nextLine();
                        bank.transferMoney(transferMoney);
                        break;

                        //deposit amount
                    case 2:
                        System.out.print("enter amount to be deposit:");
                        double amount=s.nextDouble();
                        bank.deposit(amount);
                        break;

                        //withdraw amount
                    case 3:
                        System.out.print("Enter amount to withdraw: ");
                        double wamount=s.nextDouble();
                        bank.withdraw(wamount);
                        break;

                        //current balance
                    case 4:
                        bank.showCurrentBalance();
                        break;

                        // to show account details
                    case 5:
                        acc.details();
                        break;

                        //exiting condition
                    case 6:
                        exit=true;
                        System.out.println("Exiting.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;

                }
            }

        }
        else
        {
            System.out.println("Invalid Account Id or password.");
        }


        //creating object for update class
        update Update=new update();
        if(user.verify(acc,Id, strongpass ))
        {
            System.out.println("What you want to do?");
            boolean exit=false;
            while(!exit) {
                System.out.println("1. Update Password");
                System.out.println("2. Update Phone number");
                System.out.println("3. Delete account");
                System.out.println("4. Exit");

                int choice_ = s.nextInt();
                s.nextLine();
                switch (choice_)
                {
                    case 1:
                        // new password
                        System.out.print("Enter new password: ");
                        String newpass=s.nextLine();
                        Update.changePassword(acc,newpass);
                        System.out.println("Password changed successfully!");
                        break;

                        //new phone number
                    case 2:
                        System.out.print("Enter new Phone Number: ");
                        String phnno=s.nextLine();
                        Update.changephnNo(acc,phnno);
                        System.out.print("Phone number changed successfully!");;
                        break;

                    //delete account
                    case 3:
                        System.out.print("Re-enter you account ID: ");
                        String z=s.nextLine();
                        System.out.print("Re-enter your password: ");
                        String y=s.nextLine();
                        deleteAcc(z,y);
                        break;

                    //exiting condition
                    case 4:
                        exit=true;
                        System.out.println("Exiting...");
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
        }
        else
        {
            System.out.println("Invalid Account Id or password");
        }

        System.out.println("New changed account detail-- ");
        acc.details();

        s.close();
    }
    public static void deleteAcc(String accoutId,String password){
        boolean f=false;
        for(int i=0;i<accounts.size();i++){
            if((accounts.get(i).getAccountId().equals(accoutId)&&accounts.get(i).getPassword().equals(password)))
            {
                accounts.remove(i);
                f=true;
                System.out.println("Account deleted successfully!");
                break;
            }
        }
        if(!f){
            System.out.println("Account not found!");
        }
    }
}

class createAccount
{
    public String name, dateOfBirth, phoneNo;
    protected String password;
    public String accountId;
    protected double balance=100;
    private static int nextAccNum = 1;
    private static final String bankCode = "102";
    public createAccount(String name, String dateOfBirth, String phoneNo,String password)
    {
        this.name=name;
        this.dateOfBirth=dateOfBirth;
        this.phoneNo=phoneNo;
        this.password=password;
        this.accountId=generateAccId();
    }
    public createAccount(){}

    public String getAccountId(){     //get method for account id
        return accountId;
    }
    protected String getPassword(){    //get method for password
        return password;
    }
    public String generateAccId()     //generating id
    {
        String accountNumber = String.format("%08d", nextAccNum++); // 6-digit account number
        return bankCode + accountNumber;
    }
   public void details(){
       System.out.println("Account Details--");
       System.out.println("Account Id: "+accountId);
       System.out.println("Name: "+name);
       System.out.println("Date Of Birth: "+dateOfBirth);
       System.out.println("Phone number: "+phoneNo);
       System.out.println("Cuurent Balance: "+balance);
   }
}

class useAccount
{
    public boolean verify(createAccount acc,String enterAccountid, String enterpassword )
    {
        return acc.getAccountId().equals(enterAccountid)&& acc.getPassword().equals(enterpassword);
    }
}
class bankAccount
{
    createAccount acc;
    public bankAccount(createAccount acc)
    {
        this.acc=acc;
    }
    public void deposit(double amount)
    {
        if(amount>0)
        {
            acc.balance+=amount;     //updating balance
            System.out.println("Money is deposited successfully!");
            System.out.println("Current balance = "+acc.balance);
            acc.details();
        }
        else {
            System.out.println("Invalid amount");
        }
    }
    public void withdraw(double amount)
    {
        if(acc.balance>0 && amount<=acc.balance)
        {
            acc.balance-=amount;   //updating balance
            System.out.println("Withdrawal successfully!");
            System.out.println("Current balance: "+acc.balance);
            acc.details();
        }
        else{
            System.out.println("Your current balance is less than withdrawal amount");
        }
    }
    public void showCurrentBalance()
    {
        System.out.println("Current Balance: "+acc.balance);
    }
    public void transferMoney(double amount)
    {
        System.out.println("Amount is transferred successfully!");
    }
}
class update
{

    public void changePassword(createAccount acc, String newpass)
    {
        acc.password=newpass;
        acc.details();
    }
    public void changephnNo( createAccount acc,String num)
    {
        acc.phoneNo=num;
        acc.details();
    }
}
