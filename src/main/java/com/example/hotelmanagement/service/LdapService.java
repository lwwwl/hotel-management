package com.example.hotelmanagement.service;

import com.example.hotelmanagement.dao.entity.HotelUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;
import org.springframework.ldap.NameNotFoundException;
import jakarta.annotation.PostConstruct;

import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import java.util.List;

@Service
public class LdapService {

    @Autowired
    private LdapTemplate ldapTemplate;

    @PostConstruct
    public void init() {
        ensureOuExists("people");
    }

    private void ensureOuExists(String ou) {
        Name ouDn = LdapNameBuilder.newInstance()
                .add("ou", ou)
                .build();
        try {
            ldapTemplate.lookup(ouDn);
            System.out.println("OU '" + ou + "' already exists.");
        } catch (NameNotFoundException e) {
            System.out.println("OU '" + ou + "' does not exist. Creating it...");
            BasicAttributes attributes = new BasicAttributes();
            attributes.put("objectClass", "organizationalUnit");
            attributes.put("ou", ou);
            ldapTemplate.bind(ouDn, null, attributes);
            System.out.println("OU '" + ou + "' created successfully.");
        }
    }

    protected Name buildDn(HotelUser user) {
        return LdapNameBuilder.newInstance()
                .add("ou", "people")
                .add("uid", user.getUsername())
                .build();
    }

    protected void mapToContext(HotelUser user, DirContextAdapter context) {
        context.setAttributeValue("employeeNumber", String.valueOf(user.getId()));
        context.setAttributeValue("cn", user.getUsername());
        context.setAttributeValue("sn", user.getUsername());
        context.setAttributeValue("userPassword", user.getPassword());

        // Ensure the objectClass is set correctly for a person
        context.setAttributeValues("objectClass", new String[]{"top", "person", "organizationalPerson", "inetOrgPerson"});
    }


    public void addUser(HotelUser user) {
        Name dn = buildDn(user);
        DirContextAdapter context = new DirContextAdapter(dn);
        mapToContext(user, context);
        ldapTemplate.bind(context);
    }

    public void updateUser(HotelUser user) {
        Name dn = buildDn(user);
        DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(dn);
        mapToContext(user, context);
        ldapTemplate.modifyAttributes(context);
    }

    public void deleteUser(HotelUser user) {
        Name dn = buildDn(user);
        ldapTemplate.unbind(dn);
    }
}
