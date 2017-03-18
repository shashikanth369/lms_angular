package com.gcit.lms.dao;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Genre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * Created by shash on 2/25/2017.
 */
public class BranchDAO extends BaseDAO implements ResultSetExtractor<List<Branch>>{

    //INSERT NEW BRANCH
    public void addBranch(Branch branch) throws ClassNotFoundException,SQLException {
        template.update("insert into tbl_library_branch (branchName,branchAddress) values (?,?)", new Object[] {branch.getBranchName(),branch.getBranchAddress()});
    }

    //UPDATE BRANCH
    public void updateBranch(Branch branch) throws ClassNotFoundException, SQLException{
    	 template.update("update tbl_library_branch set branchName =?,branchAddress=? where branchId =? ", new Object[] {branch.getBranchName(),branch.getBranchAddress(),branch.getBranchId()});
    }

    //DELETE BRANCH
    public void deleteBranch(Integer branchId) throws ClassNotFoundException, SQLException{
    	 template.update("delete from tbl_library_branch where branchId = ?", new Object[] {branchId});
    }

    //READ BRANCH BY NAME
    public List<Branch> readBranchByName(String name) throws ClassNotFoundException, SQLException{
        name="%"+name+"%";
        return (List<Branch>) template.query("select * from tbl_library_branch where branchName like ?", new Object[] {name}, this);
    }

    //READ ALL BRANCHES
    public List<Branch> readAllBranches(int pageNo) throws ClassNotFoundException, SQLException{
        setPageNo(pageNo);
        return (List<Branch>) template.query("select * from tbl_library_branch",this);
    }
    	//READ BRANCH BY ID 
    public Branch readBranchByID(Integer branchId) throws ClassNotFoundException, SQLException{
        List<Branch> branches = (List<Branch>) template.query("select * from tbl_library_branch where branchId =?", new Object[] {branchId}, this);
        if(branches!=null && branches.size()>0){
            return branches.get(0);
        }
        return null;
    }

    //GET COUNT OF BRANHES 
    public Integer getCount() throws ClassNotFoundException, SQLException{
    	Integer getCount = template.queryForObject("select count(*) from tbl_library_branch", Integer.class);
        return getCount;
    }

    @Override
    public List<Branch> extractData(ResultSet rs) throws SQLException {
        List<Branch> branches = new ArrayList<Branch>();

        while(rs.next()){
            Branch br = new Branch();
            br.setBranchId(rs.getInt("branchId"));
            br.setBranchName(rs.getString("branchName"));
            br.setBranchAddress(rs.getString("branchAddress"));
            branches.add(br);
        }
        return branches;
    }
    
}


