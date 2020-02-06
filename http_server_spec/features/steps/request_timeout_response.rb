class Spinach::Features::RequestTimeoutResponse < Spinach::FeatureSteps
  step 'I make a GET request to "/simple_get" without a carriage-return-line-feed' do
    @response = Requests.bad_request("GET /simple_get HTTP/1.1")
  end

  step 'my response should have status code 408' do
    status_code = Response.get_status_code_from_raw_string_response(@response)
    expect(status_code).to eq(408)
  end
end

