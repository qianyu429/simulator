package ${package}.service;

import ${package}.model.${domain};

import java.util.List;

/**
 * @author kangyonggan
 * @since ${.now?string("yyyy/MM/dd")}
 */
public interface ${domain}Service {

    List<${domain}> findAll${domain}sByPage(int pageNum);

    ${domain} get${domain}(Long id);

    int save${domain}(${domain} ${lower_domain});

    int update${domain}(${domain} ${lower_domain});

    int delete${domain}(Long id);

}