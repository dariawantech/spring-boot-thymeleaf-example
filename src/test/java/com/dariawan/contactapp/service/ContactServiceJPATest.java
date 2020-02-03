/**
 * Spring Boot + Thymeleaf Example (https://www.dariawan.com)
 * Copyright (C) 2019 Dariawan <hello@dariawan.com>
 *
 * Creative Commons Attribution-ShareAlike 4.0 International License
 *
 * Under this license, you are free to:
 * # Share - copy and redistribute the material in any medium or format
 * # Adapt - remix, transform, and build upon the material for any purpose,
 *   even commercially.
 *
 * The licensor cannot revoke these freedoms
 * as long as you follow the license terms.
 *
 * License terms:
 * # Attribution - You must give appropriate credit, provide a link to the
 *   license, and indicate if changes were made. You may do so in any
 *   reasonable manner, but not in any way that suggests the licensor
 *   endorses you or your use.
 * # ShareAlike - If you remix, transform, or build upon the material, you must
 *   distribute your contributions under the same license as the original.
 * # No additional restrictions - You may not apply legal terms or
 *   technological measures that legally restrict others from doing anything the
 *   license permits.
 *
 * Notices:
 * # You do not have to comply with the license for elements of the material in
 *   the public domain or where your use is permitted by an applicable exception
 *   or limitation.
 * # No warranties are given. The license may not give you all of
 *   the permissions necessary for your intended use. For example, other rights
 *   such as publicity, privacy, or moral rights may limit how you use
 *   the material.
 *
 * You may obtain a copy of the License at
 *   https://creativecommons.org/licenses/by-sa/4.0/
 *   https://creativecommons.org/licenses/by-sa/4.0/legalcode
 */
package com.dariawan.contactapp.service;

import com.dariawan.contactapp.domain.Contact;
import com.dariawan.contactapp.exception.ResourceNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import javax.sql.DataSource;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactServiceJPATest {

    @Autowired 
    private DataSource dataSource;
    
    @Autowired 
    private ContactService contactService;
    
    @Before
    public void cleanTestData() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "delete from contact where email not like ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%@strawhatpirat.es");
            ps.executeUpdate();
        }
    }
    
    @Test
    public void testFindAllContact() {
        List<Contact> users = contactService.findAll(1, 20);
        assertNotNull(users);
        assertTrue(users.size() == 9);
        for (Contact user : users) {
            assertNotNull(user.getId());
            assertNotNull(user.getName());
            assertNotNull(user.getEmail());
        }
    }
    
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    
    @Test
    public void testSaveUpdateDeleteContact() throws Exception{
        Contact c = new Contact();
        c.setName("Portgas D. Ace");
        c.setPhone("09012345678");
        c.setEmail("ace@whitebeard.com");
        
        contactService.save(c);
        assertNotNull(c.getId());
        
        Contact findContact = contactService.findById(c.getId());
        assertEquals("Portgas D. Ace", findContact.getName());
        assertEquals("ace@whitebeard.com", findContact.getEmail());
        
        // update record
        c.setEmail("ace@whitebeardpirat.es");
        contactService.update(c);
        
        // test after update
        findContact = contactService.findById(c.getId());
        assertEquals("ace@whitebeardpirat.es", findContact.getEmail());
        
        // test delete
        contactService.deleteById(c.getId());
        
        // query after delete
        exceptionRule.expect(ResourceNotFoundException.class);
        contactService.findById(c.getId());
    }    
}
