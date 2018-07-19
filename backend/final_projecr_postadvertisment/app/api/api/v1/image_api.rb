module Api::V1
  class ImageApi < Grape::API

    helpers do
      def permitted_params
        @permitted_params ||= declared(params, include_missing: false)
      end

      def image
        @image ||= Image.find(params[:id])
      end

      def image_params
        params[:extra_image] = ActionDispatch::Http::UploadedFile.new(params[:extra_image]) if params[:extra_image].present?
        params[:bathroom_image] = ActionDispatch::Http::UploadedFile.new(params[:bathroom_image]) if params[:bathroom_image].present?
        params[:kitchen_image] = ActionDispatch::Http::UploadedFile.new(params[:kitchen_image]) if params[:kitchen_image].present?
        ActionController::Parameters.new(params).permit(:extra_image, :bathroom_image, :kitchen_image)
      end      
    end    
    resources :images do
      # method GET
      namespace :advertisment do
        get ":id" do # get addresses/advertisment/:id
          present Image.find_by advertisment_id: params[:id] 
        end
      end
      get do      # using for test
        present Image.all
      end
      get ":id" do  #using for test
        present Image.find(params[:id])
      end

      # method POST
      params do
        optional :extra_image,    type: File
        optional :bathroom_image, type: File
        optional :kitchen_image,  type: File
      end

      post do
        # post advertisment 
        image = Image.new(image_params)
        if image.save
          present data: {
            id: image.id,
            extra_image_url: image.extra_image.url,
            bathroom_image_url: image.bathroom_image.url,
            kitchen_image_url: image.kitchen_image.url
          }
        else
          present message: "fail"
        end
      end

      # method PUT 
      # params do
      #   requires :title_post, type: String
      #   requires :price, type: Float 
      # end
      # put ":id" do
      #   adver.update_attributes(declared(params))
      #   present adver
      # end

      #method DELETE
      # delete ":id" do
      #   adver.destroy
      #   present "Destroyed"
      # end
    end
    
  end
end