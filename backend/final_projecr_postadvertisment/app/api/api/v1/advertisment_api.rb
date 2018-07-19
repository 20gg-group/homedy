module Api::V1
  class AdvertismentApi < Grape::API
    helpers do
      def adver
        @adver ||= Advertisment.find(params[:id])
      end
    end
    # before do
    #   # validate token
    #   # 686cc1da4dbf6a16ef1e
    #   authorize_user!
    # end
    resources :advertisments do
      get do
        present Advertisment.all
      end

      get ":id" do
        present Advertisment.find(params[:id])
      end

      # method POST

      params do
        requires :title_post, type: String
        requires :price, type: Float        
      end
      post do
        # post advertisment 
        Advertisment.create!(declared(params))
      end

      # method PUT 
      params do
        requires :title_post, type: String
        requires :price, type: Float 
      end
      put ":id" do
        adver.update_attributes(declared(params))
        present adver
      end

      #method DELETE
      delete ":id" do
        adver.destroy
        present "Destroyed"
      end
    end
    
  end
end