package ${package}.service.impl;

import ${package}.model.${domain};
import ${package}.service.${domain}Service;
import tk.mybatis.mapper.entity.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author kangyonggan
 * @since ${.now?string("yyyy/MM/dd")}
 */
@Service
@Transactional
public class ${domain}ServiceImpl extends BaseService<${domain}> implements ${domain}Service {

    @Override
    public int save${domain}(${domain} ${lower_domain}) {
        return super.insertSelective(${lower_domain});
    }

    @Override
    public int update${domain}(${domain} ${lower_domain}) {
        return super.updateByPrimaryKeySelective(${lower_domain});
    }

    @Override
    public int delete${domain}(Long id) {
        return super.deleteByPrimaryKey(id);
    }

    @Override
    public List<${domain}> findAll${domain}sByPage(int pageNum) {
        Example example = new Example(${domain}.class);
        example.setOrderByClause("id desc");
        return super.selectByExample4Page(pageNum, example);
    }

    @Override
    public ${domain} get${domain}(Long id) {
        return super.selectByPrimaryKey(id);
    }
}
