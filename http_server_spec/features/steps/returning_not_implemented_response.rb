class Spinach::Features::ReturningANotImplementedResponse < Spinach::FeatureSteps
  step 'I make a WONKY method request to "/simple_get"' do
    @response = Requests.bad_request("WONKY /simple_get HTTP/1.1\r\n\r\n")
  end

  step 'I make a BUSTED method request to "/simple_get"' do
    @response = Requests.bad_request("BUSTED /simple_get HTTP/1.1\r\n\r\n")
  end

  step 'my response should have status code 501' do
    status_code = Response.get_status_code_from_raw_string_response(@response)
    expect(status_code).to eq(501)
  end
end