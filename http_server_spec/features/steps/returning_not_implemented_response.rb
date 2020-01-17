class Spinach::Features::ReturningANotImplementedResponse < Spinach::FeatureSteps
  step 'I make a DELETE request to "/simple_get"' do
    @response = Requests.delete_request("/simple_get")
  end

  step 'my response should have status code 501' do
    expect(@response.status_code).to eq(501)
  end
end