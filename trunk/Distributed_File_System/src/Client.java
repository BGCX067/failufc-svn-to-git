import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.Scanner;

public class Client {
	
	public static void main(String args[]){
		
		Scanner input = new Scanner(System.in);
		String login, password;
		int option;
		
		try{
			
			Registry registry = LocateRegistry.getRegistry("localhost", 1990);
			CallerInterface caller = (CallerInterface) registry.lookup("Caller");
			
			Location directoryLocation = caller.lookUpService("Directory");
			Location fileLocation = caller.lookUpService("File");
			
			registry = LocateRegistry.getRegistry(directoryLocation.getIPAdress(), directoryLocation.getPortNumber());
			DirectoryInterface directory = (DirectoryInterface) registry.lookup("Directory");
			
			String directoryTest = directory.Teste();
			System.out.println("Directory Test: " + directoryTest);
			
			registry = LocateRegistry.getRegistry(fileLocation.getIPAdress(), fileLocation.getPortNumber());
			FileInterface file = (FileInterface) registry.lookup("File");
			
			String fileTest = file.Teste();
			System.out.println("File Test: " + fileTest);
		
			do{
				
				System.out.print("Login: ");
				login = input.nextLine();
				System.out.print("Password: ");
				password = input.nextLine();
				
				if(directory.verifyUser(login, password).equals("T")){
					break;
				}
				else{
					System.out.println("Insira Login e Senha Válidos....");
				}
				
			}while(true);
			
			do{
			
				System.out.println("######################## Magneton File System ########################");
				System.out.println("1. Criar um novo Arquivo");
				System.out.println("2. Renomear um Arquivo");
				System.out.println("3. Ler dados de um Arquivo");
				System.out.println("4. Escrever dados em um Arquivo");
				System.out.println("5. Setar os atributos de um Arquivo  (Não Implemetado)");
				System.out.println("6. Truncar um Arquivo  (Não Implemetado)");
				System.out.println("7. Deletar um Arquivo");
				System.out.println("8. Imprimir os Arquivos de um Diretório  (Não Implemetado)");
				System.out.println("0. Sair do Programa");
				
				System.out.print("Digite a opção desejada: ");
				option = input.nextInt();
				
				switch(option){
					case 1:
						int type;
						System.out.print("Digite o nome(com o path) do arquivo a ser criado: ");
						String file_name = input.next();
						
						System.out.print("Digite 1 se for um arquivo ou 0 se for diretório: ");
						do{
							type = input.nextInt();
						}while( !(type == 1 || type ==0) );
						
						
						try{
							String file_ufid = directory.AddName(file_name, type,login, password);
							file.create(file_ufid);
						}
						catch(Exception e){
							System.err.print(e.toString());
						}
						break;
						
					case 2:
						String new_file_name;
						System.out.print("Digite o nome(com path) do arquivo que deseja renomear: ");
						file_name = input.next();
						do{
							System.out.print("Digite o nome(sem path) com o qual você deseja renomear o arquivo: ");
							new_file_name = input.next();
							if(new_file_name.indexOf("-")==-1){
								break;
							}
							else{
								System.err.println("Digite o novo nome sem o path....");
							}
						}while(true);
						
						try{
							String file_capacity = directory.Lookup(file_name, login, password);
							directory.Rename(file_capacity, file_name,new_file_name);
						}
						catch(Exception e){
							System.err.print(e.toString());
						}
						break;
						
					case 3:
						System.out.print("Digite  nome do arquivo(com path) que deseja ler: ");
						file_name = input.next();
						System.out.print("Digite quantos índices você deseja ler: ");
						int lenght = input.nextInt();
						String file_ufid = directory.Lookup(file_name, login, password);
						String read = file.read(file_ufid, 0, lenght);
						System.out.println("Caracteres recuperados do arquivo....");
						System.out.print(read);
						break;
						
					case 4:
						System.out.print("Digite  nome do arquivo(com path) no qual deseja escrever e o que você deseja escrever no arquivo: ");
						file_name = input.next();
						String line = input.nextLine();
						file_ufid = directory.Lookup(file_name, login, password);
						file.write(file_ufid, line, 0);
						break;
						
					case 5:
						break;
						
					case 6:
						break;
						
					case 7:
						System.out.print("Digite  nome do arquivo(com path) que deseja deletar: ");
						file_name = input.next();
						if(!file_name.equals("-")){
							LinkedList<String> delete_list = directory.Delete(file_name, login, password);
							System.out.println("Arquivos sendo deletados...");
							file.delete(delete_list);
						}
						else{
							System.err.println("Você não pode apagar o diretório Raiz....");
						}
						break;
						
					case 0:
						System.out.println("Finalizando Magneton File System...");
						break;
						
					default:
						System.out.println("Digite uma opção válida...");
				}
				
			}while(option != 0);
			
		}
		catch(Exception e){
			System.err.println("Directory exception: " + e.toString());
            e.printStackTrace();
		}
	}
}
