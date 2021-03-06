package com.github.ebnew.ki4so.core.authentication.resolvers;

import com.github.ebnew.ki4so.core.authentication.*;
import com.github.ebnew.ki4so.core.model.KnightCredentialInfo;

/**
 * 实现了加密后的凭据转换为对应的用户主体对象的解析器
 *
 * @author Administrator
 *
 */
public class EncryCredentialToPrincipalResolver implements CredentialToPrincipalResolver {

    /**
     * Default class to support if one is not supplied.
     */
    private static final Class<KnightCredential> DEFAULT_CLASS = KnightCredential.class;

    /**
     * Class that this instance will support.
     */
    private Class<?> classToSupport = DEFAULT_CLASS;

    /**
     * Boolean to determine whether to support subclasses of the class to
     * support.
     */
    private boolean supportSubClasses = true;

    public void setSupportSubClasses(boolean supportSubClasses) {
        this.supportSubClasses = supportSubClasses;
    }

    public KnightUser resolvePrincipal(KnightCredential credential) {
        //若类型匹配，则进行转换。
        if (credential != null && this.supports(credential)) {
            KnightEncryCredential encryCredential = (KnightEncryCredential) credential;
            DefaultKnightUser principal = new DefaultKnightUser();
            //解析加密后凭据信息。
            KnightCredentialInfo encryCredentialInfo = encryCredential.getCredentialInfo();
            //设置用户名为唯一标识。
            if (encryCredentialInfo != null) {
                principal.setId(encryCredentialInfo.getUserId());
                //设置参数表为用户属性。
                principal.setAttributes(encryCredential.getParameters());
            }
            return principal;
        }
        return null;
    }

    public boolean supports(KnightCredential credential) {
        return credential != null
                && (this.classToSupport.equals(credential.getClass()) || (this.classToSupport
                .isAssignableFrom(credential.getClass()))
                && this.supportSubClasses);
    }

}
