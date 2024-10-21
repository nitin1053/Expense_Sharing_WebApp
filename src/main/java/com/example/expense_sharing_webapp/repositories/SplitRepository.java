package com.example.expense_sharing_webapp.repositories;

import com.example.expense_sharing_webapp.models.Split;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SplitRepository extends JpaRepository<Split, Long> {}
