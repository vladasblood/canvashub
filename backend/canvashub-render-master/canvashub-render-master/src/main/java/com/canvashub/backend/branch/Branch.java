package com.canvashub.backend.branch;

import com.canvashub.backend.address.Address;
import com.canvashub.backend.store.Store;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "BRANCH")
public class Branch {
    @Id
    @Column(name = "BRANCH_ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    private Store store_id;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    private Address address_id;
}
