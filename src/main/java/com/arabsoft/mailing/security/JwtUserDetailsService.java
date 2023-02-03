package com.arabsoft.mailing.security;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.Getter;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	/*@Autowired
	private ClientDao clientDao;
	
	@Getter
	private Client client;
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		client = clientDao.findByUsername(username).get(0);
		System.out.println("* Utilisateur (Client) : " +  client + " *");
		if(client==null) {
			System.out.println("* * * Error : Utilisateur (Client) non trouvé * * *");
			throw new UsernameNotFoundException("Utilisateur (Client) non trouvé");
		}
		System.out.println("* * * Validate : Utilisateur (Client) trouvé avec username : "+client.getUsername()+" * * *");
		return new User(client.getUsername(), client.getCltPassword(), new ArrayList<>());
	}*/
}
