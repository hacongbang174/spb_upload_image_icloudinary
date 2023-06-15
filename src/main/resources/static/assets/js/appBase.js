class AppBase {
    static DOMAIN_SERVER = 'http://localhost:8086'
    static API_SERVER = this.DOMAIN_SERVER + '/api'

    // static API_LOCATION_REGION = 'https://vapi.vnappmob.com/api/province/'
    // static API_LOCATION_REGION_DISTRICT = this.API_LOCATION_REGION + 'district/'
    // static API_LOCATION_REGION_WARD = this.API_LOCATION_REGION + 'ward/'

    static BASE_URL_CLOUD_IMAGE = "https://res.cloudinary.com/toanphat/image/upload";
    // static BASE_URL_CLOUD_VIDEO = "https://res.cloudinary.com/toanphat/video/upload";
    static BASE_SCALE_IMAGE = "c_limit,w_150,h_100,q_100";

    static API_PRODUCT = this.API_SERVER + '/products'
    static API_CREATE_PRODUCT = this.API_PRODUCT + '/create'
    static API_UPDATE_PRODUCT = this.API_PRODUCT + '/update'
    static API_DELETE_PRODUCT = this.API_PRODUCT + '/delete'
    static API_CATEGORY = this.API_PRODUCT + '/categories'

    static ERROR_400 = "The operation failed, please check the data again.";
    static ERROR_403 = "Access Denied! You are not authorized to perform this function.";
    static ERROR_404 = "This content has been removed or does not exist";
    static ERROR_500 = "Data saving failed, please contact the system administrator.";
    static SUCCESS_CREATED = "Successful data generation !";
    static SUCCESS_UPDATED = "Data update successful !";
    static SUCCESS_DELETED = "Delete product successfully !";

    static showDeleteConfirmDialog() {
        return Swal.fire({
            icon: 'warning',
            text: 'Are you sure to delete the selected product ?',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, please delete this client !',
            cancelButtonText: 'Cancel',
        })
    }

    static showSuccessAlert(t) {
        Swal.fire({
            icon: 'success',
            title: t,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500
        })
    }

    static showErrorAlert(t) {
        Swal.fire({
            icon: 'error',
            title: 'Warning',
            text: t,
        })
    }
}


class Product {
    constructor(id, title, price, quantity, unit, description, categoryTitle) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        this.description = description;
        this.categoryTitle = categoryTitle;
    }

}

class Avatar {
    constructor (fileId, fileName, fileFolder, fileUrl) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileFolder = fileFolder;
        this.fileUrl = fileUrl;
    }

}