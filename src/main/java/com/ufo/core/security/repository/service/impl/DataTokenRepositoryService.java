package com.ufo.core.security.repository.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufo.core.security.repository.dao.interfaces.DataTokenRepositoryDao;
import com.ufo.core.security.repository.entity.PersistentLogin;

@Service("dataTokenRepositoryService")
@Transactional
public class DataTokenRepositoryService implements PersistentTokenRepository {

    @Autowired
    private DataTokenRepositoryDao dataTokenRepositoryDao;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        dataTokenRepositoryDao.save(toPersistentLogin(token));
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentLogin pl = dataTokenRepositoryDao.findOne(series);
        pl.setToken(tokenValue);
        pl.setLastUsed(lastUsed);
        dataTokenRepositoryDao.save(pl);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return toPersistentRememberMeToken(dataTokenRepositoryDao.findOne(seriesId));
    }

    @Override
    public void removeUserTokens(String username) {
        PersistentLogin persistentLogin = new PersistentLogin();
        persistentLogin.setLoginName(username);
        dataTokenRepositoryDao.delete(persistentLogin);
    }
    
    private PersistentLogin toPersistentLogin(PersistentRememberMeToken token){
        if (token == null)
            return null;
        PersistentLogin persistentLogin = new PersistentLogin();
        persistentLogin.setId(token.getSeries());
        persistentLogin.setLoginName(token.getUsername());
        persistentLogin.setToken(token.getTokenValue());
        persistentLogin.setLastUsed(token.getDate());
        return persistentLogin;
    }
    
    private PersistentRememberMeToken toPersistentRememberMeToken(PersistentLogin pl){
        if (pl == null)
            return null;
        return new PersistentRememberMeToken(pl.getLoginName(), pl.getId(), pl.getToken(), pl.getLastUsed());
    }

}
