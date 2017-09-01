package com.abc12366.uc.model.admin.bo;

import com.abc12366.uc.model.admin.Admin;
import com.abc12366.uc.model.admin.Menu;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


/**
 * 系统角色表
 **/
@SuppressWarnings("serial")
public class RoleUpdateBO implements Serializable {

    private String id;

    @NotNull
    private Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
