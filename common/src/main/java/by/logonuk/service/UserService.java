package by.logonuk.service;

import by.logonuk.domain.Role;
import by.logonuk.domain.User;
import by.logonuk.domain.attachments.TechnicalInfo;
import by.logonuk.domain.enums.SystemRoles;
import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.repository.RoleRepository;
import by.logonuk.repository.UserRepository;
import by.logonuk.service.mailsender.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
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
        mailSender.sendMailToUserActivator(user);

        userRepository.getRoleToUser(
                user.getId(), roleRepository.findByRoleName(SystemRoles.ROLE_ANONYMOUS).getId());
        return responseUserWithRoles(user, SystemRoles.ROLE_ANONYMOUS);
    }

    @Transactional
    public User updateUserToUserRole(User user) {
        user.setActivationCode(null);
        user.setIsMailActivated(true);
        userRepository.save(user);
        userRepository.getRoleToUser(
                user.getId(), roleRepository.findByRoleName(SystemRoles.ROLE_USER).getId());
        return responseUserWithRoles(user, SystemRoles.ROLE_USER);
    }

    private User responseUserWithRoles(User savedUser, SystemRoles... systemRolesArray) {
        Set<Role> roles = savedUser.getRoles();
        Set<Role> updatedRoles = new HashSet<>();
        if (!CollectionUtils.isEmpty(roles)) {
            updatedRoles.addAll(roles);
        }
        for (SystemRoles systemRoles : systemRolesArray) {
            updatedRoles.add(roleRepository.findByRoleName(systemRoles));
        }
        savedUser.setRoles(updatedRoles);
        return savedUser;
    }

    public User updateUser(User user) {
        Optional<User> searchUser = userRepository.findByIdAndTechnicalInfoIsDeleted(user.getId(), false);
        User searchedUser = searchUser.orElseThrow(() -> new NoSuchEntityException("User with id = " + user.getId() + " does not exist"));
        TechnicalInfo technicalInfo = searchedUser.getTechnicalInfo();
        technicalInfo.setModificationDate(new Timestamp(new Date().getTime()));
        user.setTechnicalInfo(technicalInfo);

        if (user.getCredentials().getMail().equals(searchedUser.getCredentials().getMail())) {
            user.setIsMailActivated(true);
            userRepository.save(user);
            return responseUserWithRoles(user, SystemRoles.ROLE_ANONYMOUS, SystemRoles.ROLE_USER);
        } else {
            user.setIsMailActivated(false);
            user.setActivationCode(UUID.randomUUID().toString());
            userRepository.save(user);
            mailSender.sendMailToUserActivator(user);
            return responseUserWithRoles(user, SystemRoles.ROLE_ANONYMOUS);
        }
    }
}
