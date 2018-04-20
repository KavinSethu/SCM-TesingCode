package com.Util.Model.Service;

import com.Util.Model.Model.APIListResponse;
import com.Util.Model.Model.UserTerm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by user on 02-04-2018.
 */

public interface TermListService {

    @POST
    public  Call<APIListResponse<UserTerm>> getTermList(@Url String url, @Body UserTerm userTerm) ;

}
