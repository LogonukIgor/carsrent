package by.logonuk.service;

import by.logonuk.domain.Role;
import by.logonuk.domain.User;
import by.logonuk.domain.enums.SystemRoles;
import by.logonuk.repository.RoleRepository;
import by.logonuk.repository.UserRepository;
import by.logonuk.service.mailsender.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final MailSenderService mailSender;

  @Transactional
  public User createUserWithAnonymousRole(User user) {

    user.setActivationCode(UUID.randomUUID().toString());

    userRepository.save(user);

    mailSender.sendEmail(
        user.getCredentials().getMail(),
        "Mail confirmation",
        String.format(
            "Follow the link to confirm: http://localhost:8080/users/activation/%s",
            user.getActivationCode()));

    userRepository.getRoleToUser(user.getId(), roleRepository.findByRoleName(SystemRoles.ROLE_ANONYMOUS).getId());
    return user;
  }

  @Transactional
  public User updateUserToUserRole(User user){
    user.setActivationCode(null);
    user.setIsMailActivated(true);
    userRepository.save(user);
    userRepository.getRoleToUser(user.getId(), roleRepository.findByRoleName(SystemRoles.ROLE_USER).getId());
    return user;
  }

//  @Transactional()
  public User responseUserWithRoles(User savedUser, SystemRoles... systemRolesArray){
    Set<Role> roles = savedUser.getRoles();
    Set<Role> updatedRoles = new HashSet<>();
    if(!CollectionUtils.isEmpty(roles)){
      updatedRoles.addAll(roles);
    }
    for (SystemRoles systemRoles : systemRolesArray) {
      updatedRoles.add(roleRepository.findByRoleName(systemRoles));
    }
    savedUser.setRoles(updatedRoles);
    return savedUser;
  }
}
